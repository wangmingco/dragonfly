package co.wangming.dragonfly.agent.util;

import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.utility.JavaModule;

import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.any;

public class Matcher {

    public static Matcher build() {
        return new Matcher();
    }

    public Matchable<Ignored> type(ElementMatcher<? super TypeDescription> typeMatcher) {
        return type(new RawMatcher.ForElementMatchers(typeMatcher));
    }

    public Ignored type(RawMatcher matcher) {
        return new Ignoring(matcher);
    }

    interface RawMatcher {

        boolean matches(TypeDescription typeDescription,
                        ClassLoader classLoader,
                        JavaModule module,
                        Class<?> classBeingRedefined,
                        ProtectionDomain protectionDomain);

        /**
         * A matcher that always or never matches a type.
         */
        enum Trivial implements RawMatcher {

            /**
             * Always matches a type.
             */
            MATCHING(true),

            /**
             * Never matches a type.
             */
            NON_MATCHING(false);

            /**
             * {@code true} if this matcher always matches a type.
             */
            private final boolean matches;

            /**
             * Creates a new trivial raw matcher.
             *
             * @param matches {@code true} if this matcher always matches a type.
             */
            Trivial(boolean matches) {
                this.matches = matches;
            }

            /**
             * {@inheritDoc}
             */
            public boolean matches(TypeDescription typeDescription,
                                   ClassLoader classLoader,
                                   JavaModule module,
                                   Class<?> classBeingRedefined,
                                   ProtectionDomain protectionDomain) {
                return matches;
            }
        }

        /**
         * A raw matcher indicating the state of a type's class loading.
         */
        enum ForLoadState implements RawMatcher {

            /**
             * Indicates that a type was already loaded.
             */
            LOADED(false),

            /**
             * Indicates that a type was not yet loaded.
             */
            UNLOADED(true);

            /**
             * {@code true} if a type is expected to be unloaded..
             */
            private final boolean unloaded;

            /**
             * Creates a new load state matcher.
             *
             * @param unloaded {@code true} if a type is expected to be unloaded..
             */
            ForLoadState(boolean unloaded) {
                this.unloaded = unloaded;
            }

            /**
             * {@inheritDoc}
             */
            public boolean matches(TypeDescription typeDescription,
                                   ClassLoader classLoader,
                                   JavaModule module,
                                   Class<?> classBeingRedefined,
                                   ProtectionDomain protectionDomain) {
                return classBeingRedefined == null == unloaded;
            }
        }

        /**
         * Only matches loaded types that can be fully resolved. Types with missing dependencies might not be
         * resolvable and can therefore trigger errors during redefinition.
         */
        enum ForResolvableTypes implements RawMatcher {

            /**
             * The singleton instance.
             */
            INSTANCE;

            /**
             * {@inheritDoc}
             */
            public boolean matches(TypeDescription typeDescription,
                                   ClassLoader classLoader,
                                   JavaModule module,
                                   Class<?> classBeingRedefined,
                                   ProtectionDomain protectionDomain) {
                if (classBeingRedefined != null) {
                    try {
                        return Class.forName(classBeingRedefined.getName(), true, classLoader) == classBeingRedefined;
                    } catch (Throwable ignored) {
                        return false;
                    }
                } else {
                    return true;
                }
            }

            /**
             * Returns an inverted version of this matcher.
             *
             * @return An inverted version of this matcher.
             */
            public RawMatcher inverted() {
                return new Inversion(this);
            }
        }

        /**
         * A conjunction of two raw matchers.
         */
        @HashCodeAndEqualsPlugin.Enhance
        class Conjunction implements RawMatcher {

            /**
             * The matchers to apply in their application order.
             */
            private final List<RawMatcher> matchers;

            /**
             * Creates a new conjunction of two raw matchers.
             *
             * @param matcher The matchers to apply in their application order.
             */
            protected Conjunction(RawMatcher... matcher) {
                this(Arrays.asList(matcher));
            }

            /**
             * Creates a new conjunction of two raw matchers.
             *
             * @param matchers The matchers to apply in their application order.
             */
            protected Conjunction(List<? extends RawMatcher> matchers) {
                this.matchers = new ArrayList<RawMatcher>(matchers.size());
                for (RawMatcher matcher : matchers) {
                    if (matcher instanceof Conjunction) {
                        this.matchers.addAll(((Conjunction) matcher).matchers);
                    } else if (matcher != Trivial.MATCHING) {
                        this.matchers.add(matcher);
                    }
                }
            }

            /**
             * {@inheritDoc}
             */
            public boolean matches(TypeDescription typeDescription,
                                   ClassLoader classLoader,
                                   JavaModule module,
                                   Class<?> classBeingRedefined,
                                   ProtectionDomain protectionDomain) {
                for (RawMatcher matcher : matchers) {
                    if (!matcher.matches(typeDescription, classLoader, module, classBeingRedefined, protectionDomain)) {
                        return false;
                    }
                }
                return true;
            }
        }

        /**
         * A disjunction of two raw matchers.
         */
        @HashCodeAndEqualsPlugin.Enhance
        class Disjunction implements RawMatcher {

            /**
             * The matchers to apply in their application order.
             */
            private final List<RawMatcher> matchers;

            /**
             * Creates a new conjunction of two raw matchers.
             *
             * @param matcher The matchers to apply in their application order.
             */
            protected Disjunction(RawMatcher... matcher) {
                this(Arrays.asList(matcher));
            }

            /**
             * Creates a new conjunction of two raw matchers.
             *
             * @param matchers The matchers to apply in their application order.
             */
            protected Disjunction(List<? extends RawMatcher> matchers) {
                this.matchers = new ArrayList<RawMatcher>(matchers.size());
                for (RawMatcher matcher : matchers) {
                    if (matcher instanceof Disjunction) {
                        this.matchers.addAll(((Disjunction) matcher).matchers);
                    } else if (matcher != Trivial.NON_MATCHING) {
                        this.matchers.add(matcher);
                    }
                }
            }

            /**
             * {@inheritDoc}
             */
            public boolean matches(TypeDescription typeDescription,
                                   ClassLoader classLoader,
                                   JavaModule module,
                                   Class<?> classBeingRedefined,
                                   ProtectionDomain protectionDomain) {
                for (RawMatcher matcher : matchers) {
                    if (matcher.matches(typeDescription, classLoader, module, classBeingRedefined, protectionDomain)) {
                        return true;
                    }
                }
                return false;
            }
        }

        /**
         * A raw matcher that inverts a raw matcher's result.
         */
        @HashCodeAndEqualsPlugin.Enhance
        class Inversion implements RawMatcher {

            /**
             * The matcher to invert.
             */
            private final RawMatcher matcher;

            /**
             * Creates a raw matcher that inverts its result.
             *
             * @param matcher The matcher to invert.
             */
            public Inversion(RawMatcher matcher) {
                this.matcher = matcher;
            }

            /**
             * {@inheritDoc}
             */
            public boolean matches(TypeDescription typeDescription,
                                   ClassLoader classLoader,
                                   JavaModule module,
                                   Class<?> classBeingRedefined,
                                   ProtectionDomain protectionDomain) {
                return !matcher.matches(typeDescription, classLoader, module, classBeingRedefined, protectionDomain);
            }
        }

        /**
         * A raw matcher implementation that checks a {@link TypeDescription}
         * and its {@link java.lang.ClassLoader} against two suitable matchers in order to determine if the matched
         * type should be instrumented.
         */
        @HashCodeAndEqualsPlugin.Enhance
        class ForElementMatchers implements RawMatcher {

            /**
             * The type matcher to apply to a {@link TypeDescription}.
             */
            private final ElementMatcher<? super TypeDescription> typeMatcher;

            /**
             * The class loader matcher to apply to a {@link java.lang.ClassLoader}.
             */
            private final ElementMatcher<? super ClassLoader> classLoaderMatcher;

            /**
             * A module matcher to apply to a {@code java.lang.Module}.
             */
            private final ElementMatcher<? super JavaModule> moduleMatcher;

            public ForElementMatchers(ElementMatcher<? super TypeDescription> typeMatcher) {
                this(typeMatcher, any());
            }

            public ForElementMatchers(ElementMatcher<? super TypeDescription> typeMatcher,
                                      ElementMatcher<? super ClassLoader> classLoaderMatcher) {
                this(typeMatcher, classLoaderMatcher, any());
            }

            public ForElementMatchers(ElementMatcher<? super TypeDescription> typeMatcher,
                                      ElementMatcher<? super ClassLoader> classLoaderMatcher,
                                      ElementMatcher<? super JavaModule> moduleMatcher) {
                this.typeMatcher = typeMatcher;
                this.classLoaderMatcher = classLoaderMatcher;
                this.moduleMatcher = moduleMatcher;
            }

            /**
             * {@inheritDoc}
             */
            public boolean matches(TypeDescription typeDescription,
                                   ClassLoader classLoader,
                                   JavaModule module,
                                   Class<?> classBeingRedefined,
                                   ProtectionDomain protectionDomain) {
                return moduleMatcher.matches(module) && classLoaderMatcher.matches(classLoader) && typeMatcher.matches(typeDescription);
            }
        }
    }

    public interface Matchable<T extends Matchable<T>> {

        /**
         * Defines a matching that is positive if both the previous matcher and the supplied matcher are matched. When matching a
         * type, class loaders are not considered.
         *
         * @param typeMatcher A matcher for the type being matched.
         * @return A chained matcher.
         */
        T and(ElementMatcher<? super TypeDescription> typeMatcher);

        /**
         * Defines a matching that is positive if both the previous matcher and the supplied matcher are matched.
         *
         * @param typeMatcher        A matcher for the type being matched.
         * @param classLoaderMatcher A matcher for the type's class loader.
         * @return A chained matcher.
         */
        T and(ElementMatcher<? super TypeDescription> typeMatcher, ElementMatcher<? super ClassLoader> classLoaderMatcher);

        /**
         * Defines a matching that is positive if both the previous matcher and the supplied matcher are matched.
         *
         * @param typeMatcher        A matcher for the type being matched.
         * @param classLoaderMatcher A matcher for the type's class loader.
         * @param moduleMatcher      A matcher for the type's module. On a JVM that does not support modules, the Java module is represented by {@code null}.
         * @return A chained matcher.
         */
        T and(ElementMatcher<? super TypeDescription> typeMatcher,
              ElementMatcher<? super ClassLoader> classLoaderMatcher,
              ElementMatcher<? super JavaModule> moduleMatcher);

        /**
         * Defines a matching that is positive if both the previous matcher and the supplied matcher are matched.
         *
         * @param rawMatcher A raw matcher for the type being matched.
         * @return A chained matcher.
         */
        T and(RawMatcher rawMatcher);

        /**
         * Defines a matching that is positive if the previous matcher or the supplied matcher are matched. When matching a
         * type, the class loader is not considered.
         *
         * @param typeMatcher A matcher for the type being matched.
         * @return A chained matcher.
         */
        T or(ElementMatcher<? super TypeDescription> typeMatcher);

        /**
         * Defines a matching that is positive if the previous matcher or the supplied matcher are matched.
         *
         * @param typeMatcher        A matcher for the type being matched.
         * @param classLoaderMatcher A matcher for the type's class loader.
         * @return A chained matcher.
         */
        T or(ElementMatcher<? super TypeDescription> typeMatcher, ElementMatcher<? super ClassLoader> classLoaderMatcher);

        /**
         * Defines a matching that is positive if the previous matcher or the supplied matcher are matched.
         *
         * @param typeMatcher        A matcher for the type being matched.
         * @param classLoaderMatcher A matcher for the type's class loader.
         * @param moduleMatcher      A matcher for the type's module. On a JVM that does not support modules, the Java module is represented by {@code null}.
         * @return A chained matcher.
         */
        T or(ElementMatcher<? super TypeDescription> typeMatcher,
             ElementMatcher<? super ClassLoader> classLoaderMatcher,
             ElementMatcher<? super JavaModule> moduleMatcher);

        /**
         * Defines a matching that is positive if the previous matcher or the supplied matcher are matched.
         *
         * @param rawMatcher A raw matcher for the type being matched.
         * @return A chained matcher.
         */
        T or(RawMatcher rawMatcher);

        boolean matches(TypeDescription typeDescription,
                        ClassLoader classLoader,
                        JavaModule module,
                        Class<?> classBeingRedefined,
                        ProtectionDomain protectionDomain);
    }

    protected abstract static class MatchableDelegator<S extends Matchable<S>> implements Matchable<S> {

        /**
         * {@inheritDoc}
         */
        public S and(ElementMatcher<? super TypeDescription> typeMatcher) {
            return and(typeMatcher, any());
        }

        /**
         * {@inheritDoc}
         */
        public S and(ElementMatcher<? super TypeDescription> typeMatcher, ElementMatcher<? super ClassLoader> classLoaderMatcher) {
            return and(typeMatcher, classLoaderMatcher, any());
        }

        /**
         * {@inheritDoc}
         */
        public S and(ElementMatcher<? super TypeDescription> typeMatcher,
                     ElementMatcher<? super ClassLoader> classLoaderMatcher,
                     ElementMatcher<? super JavaModule> moduleMatcher) {
            return and(new RawMatcher.ForElementMatchers(typeMatcher, classLoaderMatcher, moduleMatcher));
        }

        /**
         * {@inheritDoc}
         */
        public S or(ElementMatcher<? super TypeDescription> typeMatcher) {
            return or(typeMatcher, any());
        }

        /**
         * {@inheritDoc}
         */
        public S or(ElementMatcher<? super TypeDescription> typeMatcher, ElementMatcher<? super ClassLoader> classLoaderMatcher) {
            return or(typeMatcher, classLoaderMatcher, any());
        }

        /**
         * {@inheritDoc}
         */
        public S or(ElementMatcher<? super TypeDescription> typeMatcher,
                    ElementMatcher<? super ClassLoader> classLoaderMatcher,
                    ElementMatcher<? super JavaModule> moduleMatcher) {
            return or(new RawMatcher.ForElementMatchers(typeMatcher, classLoaderMatcher, moduleMatcher));
        }
    }

    interface Ignored extends Matchable<Ignored> {
        /* this is merely a unionizing interface that does not declare methods */
    }

    @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
    public class Ignoring extends MatchableDelegator<Ignored> implements Ignored {

        /**
         * A matcher for identifying types that should not be instrumented.
         */
        private final RawMatcher rawMatcher;

        /**
         * Creates a new agent builder for further specifying what types to ignore.
         *
         * @param rawMatcher A matcher for identifying types that should not be instrumented.
         */
        protected Ignoring(RawMatcher rawMatcher) {
            this.rawMatcher = rawMatcher;
        }

        /**
         * {@inheritDoc}
         *
         * @return
         */
        public Ignored and(RawMatcher rawMatcher) {
            return new Ignoring(new Conjunction(this.rawMatcher, rawMatcher));
        }

        /**
         * {@inheritDoc}
         */
        public Ignored or(RawMatcher rawMatcher) {
            return new Ignoring(new Disjunction(this.rawMatcher, rawMatcher));
        }

        public boolean matches(TypeDescription typeDescription,
                               ClassLoader classLoader,
                               JavaModule module,
                               Class<?> classBeingRedefined,
                               ProtectionDomain protectionDomain) {
            return rawMatcher.matches(typeDescription, classLoader, module, classBeingRedefined, protectionDomain);
        }
    }


    /**
     * A conjunction of two raw matchers.
     */
    @HashCodeAndEqualsPlugin.Enhance
    class Conjunction implements RawMatcher {

        /**
         * The matchers to apply in their application order.
         */
        private final List<RawMatcher> matchers;

        /**
         * Creates a new conjunction of two raw matchers.
         *
         * @param matcher The matchers to apply in their application order.
         */
        protected Conjunction(RawMatcher... matcher) {
            this(Arrays.asList(matcher));
        }

        /**
         * Creates a new conjunction of two raw matchers.
         *
         * @param matchers The matchers to apply in their application order.
         */
        protected Conjunction(List<? extends RawMatcher> matchers) {
            this.matchers = new ArrayList<RawMatcher>(matchers.size());
            for (RawMatcher matcher : matchers) {
                if (matcher instanceof Conjunction) {
                    this.matchers.addAll(((Conjunction) matcher).matchers);
                } else if (matcher != Trivial.MATCHING) {
                    this.matchers.add(matcher);
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        public boolean matches(TypeDescription typeDescription,
                               ClassLoader classLoader,
                               JavaModule module,
                               Class<?> classBeingRedefined,
                               ProtectionDomain protectionDomain) {
            for (RawMatcher matcher : matchers) {
                if (!matcher.matches(typeDescription, classLoader, module, classBeingRedefined, protectionDomain)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * A disjunction of two raw matchers.
     */
    @HashCodeAndEqualsPlugin.Enhance
    class Disjunction implements RawMatcher {

        /**
         * The matchers to apply in their application order.
         */
        private final List<RawMatcher> matchers;

        /**
         * Creates a new conjunction of two raw matchers.
         *
         * @param matcher The matchers to apply in their application order.
         */
        protected Disjunction(RawMatcher... matcher) {
            this(Arrays.asList(matcher));
        }

        /**
         * Creates a new conjunction of two raw matchers.
         *
         * @param matchers The matchers to apply in their application order.
         */
        protected Disjunction(List<? extends RawMatcher> matchers) {
            this.matchers = new ArrayList<RawMatcher>(matchers.size());
            for (RawMatcher matcher : matchers) {
                if (matcher instanceof Disjunction) {
                    this.matchers.addAll(((Disjunction) matcher).matchers);
                } else if (matcher != Trivial.NON_MATCHING) {
                    this.matchers.add(matcher);
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        public boolean matches(TypeDescription typeDescription,
                               ClassLoader classLoader,
                               JavaModule module,
                               Class<?> classBeingRedefined,
                               ProtectionDomain protectionDomain) {
            for (RawMatcher matcher : matchers) {
                if (matcher.matches(typeDescription, classLoader, module, classBeingRedefined, protectionDomain)) {
                    return true;
                }
            }
            return false;
        }
    }

}
