package com.ezraaudivano.kostku;


import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.ezraaudivano.kostku.ui.pengumuman.PengumumanFragment;
import com.ezraaudivano.kostku.ui.pengumuman.TambahEditPengumuman;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddPengumumanTest {

    @Rule
    public ActivityTestRule<MenuActivity> mActivityTestRule = new ActivityTestRule<>(MenuActivity.class);

    @Test
    public void addPengumumanTest() {


        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.nav_pengumuman),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                3),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fabAdd),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.frame_view_pengumuman),
                                        1),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.etSubjudul),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.subJudulPengumumanLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("subjudul"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.etKonten),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.kontenPengumumanLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("konten"), closeSoftKeyboard());



        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnSimpan), withText("Simpan"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.etJudul),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.judulPengumumanLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("judul"), closeSoftKeyboard());

            ViewInteraction textInputEditText6 = onView(
                    allOf(withId(R.id.etSubjudul), withText("subjudul"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.subJudulPengumumanLayout),
                                            0),
                                    1),
                            isDisplayed()));
            textInputEditText6.perform(replaceText(""));

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.etSubjudul),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.subJudulPengumumanLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText7.perform(closeSoftKeyboard());



        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnSimpan), withText("Simpan"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.etSubjudul),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.subJudulPengumumanLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText8.perform(click());

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.etSubjudul),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.subJudulPengumumanLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText9.perform(replaceText("subjudul"), closeSoftKeyboard());



        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.etKonten), withText("konten"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.kontenPengumumanLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText10.perform(replaceText(""));

        ViewInteraction textInputEditText11 = onView(
                allOf(withId(R.id.etKonten),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.kontenPengumumanLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText11.perform(closeSoftKeyboard());



        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btnSimpan), withText("Simpan"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1),
                        isDisplayed()));
        materialButton4.perform(click());

//        ViewInteraction textInputEditText12 = onView(
//                allOf(withId(R.id.etKonten),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.kontenPengumumanLayout),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textInputEditText12.perform(click());

        ViewInteraction textInputEditText13 = onView(
                allOf(withId(R.id.etKonten),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.kontenPengumumanLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText13.perform(replaceText("konten"), closeSoftKeyboard());



        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btnSimpan), withText("Simpan"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1),
                        isDisplayed()));
        materialButton5.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
