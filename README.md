# Fragment Stack

This is a Android library to manage multiple page stacks of Fragment.

You have a benefit of using the fragment like just pages simply.

This provides mainly two functions.

- Managing indipendent multiple fragment stacks like pages.
- Support of adding page like life cycle. When a fragment is poped or pushed, the life cycle is called.

# Installation

## Gradle

```gradle
compile 'io.github.oniatsu:fragment-stack:0.1.0'
```

# Basic usage

## Setup

### 1. Register a FragmentStack on the Activity's `onCreate()`

```java
FragmentStack.register(this, R.id.fragment_container, new DefaultFragment());
```

If you use a fragment for container, write the code on the Fragment's `onActivityCreated()`.

### 2. Unregister the FragmentStack on the Activity/Fragment's `onDestroy()`

```java
FragmentStack.unregister(this);
```

### 2. Inherit `FragmentPagerLifeCycleFragment` on every page fragments.

```java
public class NewFragment extends FragmentPagerLifeCycleFragment {
    // ...
}
```

This provides fragment page life cycles.
- onForeground
  - Called when the fragment page become foreground to the user.
  - This is called after Fragment#onResume().
- onBackground
  - Called when the Fragment page become background to the user.
  - This is called before Fragment#onPouse().

The page life cycles is called on this order.
- onResume → onForeground → onBackground → onPause

## Add and remove, anywhere and anytime.

You can get the registered FragmentStack like this.
```java
FragmentStack.of(this)                     // If you want to get it on same activity/fragment
// or
FragmentStack.of(RegisteredActivity.class) // If you want to get the registered activity on different activity/fragment
// or
FragmentStack.of(RegisteredFragment.class) // If you want to get the registered fragment on different activity/fragment
```

### Add
```java
FragmentStack.of(this).add(new NewFragment());
```

### Remove and back
```java
FragmentStack.of(this).back();
```

# And, other useful APIs are prepared.

## Page APIs

### Check if it has pages.
```java
FragmentStack.of(this).hasPage();
```

### Back some count times.
```java
FragmentStack.of(this).back(3);
```

### Back to specific taged page.
```java
FragmentStack.of(this).back(PastFragment.class, 0);
// or
FragmentStack.of(this).back(PastFragment.class, FragmentManager.POP_BACK_STACK_INCLUSIVE);
```

### Clear all.
```java
FragmentStack.of(this).clear();
```

## Applicaiton global config
```java
FragmentStack.globalConfig()
        .transitionInterceptor((fragmentPagerManager, fragmentTransaction) -> {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        })
        .setup();
```

## A FragmentStack local config
```java
FragmentStack.of(this).localConfig()
        .transitionInterceptor((fragmentPagerManager, fragmentTransaction) -> {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        })
        .setup();
```

## Each fragment transaction config
```java
FragmentStack.of(this).add(new NewFragment(), (pageManager, fragmentTransaction) -> {
    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
});
```

# FAQ

## If you have to inherit another Fragment and you cannot inherit `FragmentPagerLifeCycleFragment`

Implements `FragmentPagerLifeCycleListener` and call onForeground/onBackground on onResume/onPause manualy.

```java
public abstract YourFragment extends YourBaseFragment implements FragmentPagerLifeCycleListener {

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

