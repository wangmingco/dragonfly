package co.wangming.dragonfly.agent.bytebuddy;

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
        return new Ignoring((new ForElementMatchers(typeMatcher)));
    }

    interface RawMatcher {

        boolean matches(TypeDescription typeDescription,
                        ClassLoader classLoader,
                        JavaModule module,
                        Class<?> classBeingRedefined,
                        ProtectionDomain protectionDomain);

        enum Trivial implements RawMatcher {
            MATCHING(true),
            NON_MATCHING(false);

            private final boolean matches;

            Trivial(boolean matches) {
                this.matches = matches;
            }

            public boolean matches(TypeDescription typeDescription,
                                   ClassLoader classLoader,
                                   JavaModule module,
                                   Class<?> classBeingRedefined,
                                   ProtectionDomain protectionDomain) {
                return matches;
            }
        }

    }


    public interface Matchable<T extends Matchable<T>> {

        T and(ElementMatcher<? super TypeDescription> typeMatcher);

        T and(ElementMatcher<? super TypeDescription> typeMatcher, ElementMatcher<? super ClassLoader> classLoaderMatcher);

        T and(ElementMatcher<? super TypeDescription> typeMatcher,
              ElementMatcher<? super ClassLoader> classLoaderMatcher,
              ElementMatcher<? super JavaModule> moduleMatcher);

        T and(RawMatcher rawMatcher);

        T or(ElementMatcher<? super TypeDescription> typeMatcher);

        T or(ElementMatcher<? super TypeDescription> typeMatcher, ElementMatcher<? super ClassLoader> classLoaderMatcher);

        T or(ElementMatcher<? super TypeDescription> typeMatcher,
             ElementMatcher<? super ClassLoader> classLoaderMatcher,
             ElementMatcher<? super JavaModule> moduleMatcher);

        T or(RawMatcher rawMatcher);

        boolean matches(TypeDescription typeDescription,
                        ClassLoader classLoader,
                        JavaModule module,
                        Class<?> classBeingRedefined,
                        ProtectionDomain protectionDomain);
    }

    interface Ignored extends Matchable<Ignored> {
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class ForElementMatchers implements RawMatcher {

        private final ElementMatcher<? super TypeDescription> typeMatcher;

        private final ElementMatcher<? super ClassLoader> classLoaderMatcher;

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

        public boolean matches(TypeDescription typeDescription,
                               ClassLoader classLoader,
                               JavaModule module,
                               Class<?> classBeingRedefined,
                               ProtectionDomain protectionDomain) {
            return moduleMatcher.matches(module) && classLoaderMatcher.matches(classLoader) && typeMatcher.matches(typeDescription);
        }
    }

    protected abstract static class MatchableDelegator<S extends Matchable<S>> implements Matchable<S> {

        public S and(ElementMatcher<? super TypeDescription> typeMatcher) {
            return and(typeMatcher, any());
        }

        public S and(ElementMatcher<? super TypeDescription> typeMatcher, ElementMatcher<? super ClassLoader> classLoaderMatcher) {
            return and(typeMatcher, classLoaderMatcher, any());
        }

        public S and(ElementMatcher<? super TypeDescription> typeMatcher,
                     ElementMatcher<? super ClassLoader> classLoaderMatcher,
                     ElementMatcher<? super JavaModule> moduleMatcher) {
            return and(new ForElementMatchers(typeMatcher, classLoaderMatcher, moduleMatcher));
        }

        public S or(ElementMatcher<? super TypeDescription> typeMatcher) {
            return or(typeMatcher, any());
        }

        public S or(ElementMatcher<? super TypeDescription> typeMatcher, ElementMatcher<? super ClassLoader> classLoaderMatcher) {
            return or(typeMatcher, classLoaderMatcher, any());
        }

        public S or(ElementMatcher<? super TypeDescription> typeMatcher,
                    ElementMatcher<? super ClassLoader> classLoaderMatcher,
                    ElementMatcher<? super JavaModule> moduleMatcher) {
            return or(new ForElementMatchers(typeMatcher, classLoaderMatcher, moduleMatcher));
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    class Conjunction implements RawMatcher {

        private final List<RawMatcher> matchers;

        protected Conjunction(RawMatcher... matcher) {
            this(Arrays.asList(matcher));
        }

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

    @HashCodeAndEqualsPlugin.Enhance
    class Disjunction implements RawMatcher {

        private final List<RawMatcher> matchers;

        protected Disjunction(RawMatcher... matcher) {
            this(Arrays.asList(matcher));
        }

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

    @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
    public class Ignoring extends MatchableDelegator<Ignored> implements Ignored {

        private final RawMatcher rawMatcher;

        protected Ignoring(RawMatcher rawMatcher) {
            this.rawMatcher = rawMatcher;
        }

        public Ignored and(RawMatcher rawMatcher) {
            return new Ignoring(new Conjunction(this.rawMatcher, rawMatcher));
        }

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

}

