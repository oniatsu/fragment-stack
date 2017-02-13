# Fragment Stack

This is a Android library to manage multiple page stacks of Fragment.

You have a benefit of using the Fragment like just pages easily.

It provides mainly two functions.

- Managing indipendent multiple Fragment stacks like pages.
- Support of adding life cycle like pages. When a Fragment is poped or pushed, onForeground / onBackground is called.

<img src="https://cloud.githubusercontent.com/assets/5919569/22864825/be849ad4-f19b-11e6-9a35-7937895c1c01.gif" width="280">

# Installation

## Gradle

```gradle
compile 'io.github.oniatsu:fragment-stack:0.1.0'
```

# Basic usage

## Setup

### 1. Register a FragmentStack

If you use Activity as Fargment container, write on `Activity's onCreate()`.
If you use Fragment as it, write on Fragment's `onActivityCreated()`.
```java
FragmentStack.register(this, R.id.fragment_container, new DefaultFragment());
```

Arguments
- 1st: To be registered Activity / Fragment as page Fragment container.
- 2nd: Fragment container's layout id.
- 3rd: Default Fragment to be initialized first on the container.

### 2. Unregister the FragmentStack

On Activity / Fragment's `onDestroy()`.
```java
FragmentStack.unregister(this);
```

### 3. Inherit `FragmentPagerLifeCycleFragment` on every page Fragments you use.
```java
public class NewFragment extends FragmentPagerLifeCycleFragment {
    // ...
}
```

It provides two fragment page's life cycles.
- onForeground
  - Called when the fragment page become foreground to the user.
  - This is called after `Fragment#onResume()`.
- onBackground
  - Called when the Fragment page become background to the user.
  - This is called before `Fragment#onPouse()`.

The page life cycles is called on this order.
- `onResume` → `onForeground` → `onBackground` → `onPause`

## Add and remove pages, anywhere and anytime.

You can get the registered FragmentStack on any class as below.
```java
// If you want to get it on same Activity / Fragment to the page Fragment's container
FragmentStack.of(this)                     
// or, if you want it on a Activity different from the page Fragment's container
FragmentStack.of(RegisteredActivity.class)
// or, if you want it on a Fragment different from the page Fragment's container
FragmentStack.of(RegisteredFragment.class)
```

### Add
```java
FragmentStack.of(this).add(new NewFragment());
```

### Remove and back
```java
FragmentStack.of(this).back();
```

# Other flexible page stack APIs are prepared.

## Page APIs

### Check if it has pages.
```java
FragmentStack.of(this).hasPage();
```

### Back some count times.
```java
FragmentStack.of(this).back(3);
```

### Back to the specific fragment page.
```java
FragmentStack.of(this).back(PastFragment.class, 0);
// or
FragmentStack.of(this).back(PastFragment.class, FragmentManager.POP_BACK_STACK_INCLUSIVE);
```

### Clear all.
```java
FragmentStack.of(this).clear();
```

## Applicaiton global config.
```java

FragmentStack.globalConfig()
        .transitionInterceptor(new FragmentPagerTransitionInterceptor() {
            @Override
            public void onTransaction(PageManager pageManager, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            }
        })
        .setup();
```

## A FragmentStack local config.
```java
FragmentStack.of(this).localConfig()
        .transitionInterceptor(new FragmentPagerTransitionInterceptor() {
            @Override
            public void onTransaction(PageManager pageManager, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            }
        })
        .setup();
```

## Each fragment transaction config.
```java
FragmentStack.of(this).add(new NewFragment(), new FragmentPagerTransitionInterceptor() {
    @Override
    public void onTransaction(PageManager pageManager, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }
});
```

# FAQ

## If you need to inherit another Fragment and you cannot inherit `FragmentPagerLifeCycleFragment` on your page Fragments

Implements `FragmentPagerLifeCycleListener` interface and call `onForeground` / `onBackground` on `onResume` / `onPause` manualy.

```java
public abstract YourFragment extends YourSuperFragment implements FragmentPagerLifeCycleListener {

    @Override
    public void onForeground() {
    }

    @Override
    public void onBackground() {
    }

    @Override
    public void onResume() {
        super.onResume();
        onForeground();
    }

    @Override
    public void onPause() {
        onBackground();
        super.onPause();
    }
```

