package com.ezraaudivano.kostku;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddKostTest {

    @Rule
    public ActivityTestRule<AddKostActivity> mActivityTestRule = new ActivityTestRule<>(AddKostActivity.class);

    @Test
    public void addKostTest() {

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.addKost), withText("ADD KOST"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.containerAddKost),
                                        0),
                                7)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.kost_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.namaKos_layout),
                                        0),
                                1)));
        textInputEditText.perform(scrollTo(), replaceText("Kost Ijo"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.addressKost),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.address_Layout),
                                        0),
                                1)));
        textInputEditText2.perform(scrollTo(), replaceText("Jalan Duren no. 9"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.SizeKost),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.kota_layout),
                                        0),
                                1)));
        textInputEditText3.perform(scrollTo(), replaceText("500"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.sisa_kamar),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.sisa_layout),
                                        0),
                                1)));
        textInputEditText4.perform(scrollTo(), replaceText("14"), closeSoftKeyboard());



        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.addKost), withText("ADD KOST"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.containerAddKost),
                                        0),
                                7)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction materialRadioButton = onView(
                allOf(withId(R.id.radioReg), withText("Reguler"),
                        childAtPosition(
                                allOf(withId(R.id.radioGroupXML),
                                        childAtPosition(
                                                withId(R.id.type_Layout),
                                                2)),
                                1)));
        materialRadioButton.perform(scrollTo(), click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.kost_name), withText("Kost Ijo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.namaKos_layout),
                                        0),
                                1)));
        textInputEditText5.perform(scrollTo(), replaceText(""));

//        ViewInteraction textInputEditText6 = onView(
//                allOf(withId(R.id.kost_name),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.namaKos_layout),
//                                        1),
//                                0),
//                        isDisplayed()));
//        textInputEditText6.perform(closeSoftKeyboard());



        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.addKost), withText("ADD KOST"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.containerAddKost),
                                        0),
                                7)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.kost_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.namaKos_layout),
                                        0),
                                1)));
        textInputEditText7.perform(scrollTo(), click());

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.kost_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.namaKos_layout),
                                        0),
                                0)));
        textInputEditText8.perform(scrollTo(), replaceText("Kost Ijo"), closeSoftKeyboard());

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.addressKost), withText("Jalan Duren no. 9"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.address_Layout),
                                        0),
                                1)));
        textInputEditText9.perform(scrollTo(), replaceText(""));

//        ViewInteraction textInputEditText10 = onView(
//                allOf(withId(R.id.addressKost),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.address_Layout),
//                                        0),
//                                2),
//                        isDisplayed()));
//        textInputEditText10.perform(closeSoftKeyboard());



        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.addKost), withText("ADD KOST"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.containerAddKost),
                                        0),
                                7)));
        materialButton4.perform(scrollTo(), click());

        ViewInteraction textInputEditText11 = onView(
                allOf(withId(R.id.addressKost),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.address_Layout),
                                        0),
                                1)));
        textInputEditText11.perform(scrollTo(), click());

        ViewInteraction textInputEditText12 = onView(
                allOf(withId(R.id.addressKost),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.address_Layout),
                                        0),
                                0)));
        textInputEditText12.perform(scrollTo(), replaceText("Jalan duren no.9"), closeSoftKeyboard());

        ViewInteraction textInputEditText13 = onView(
                allOf(withId(R.id.SizeKost), withText("500"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.kota_layout),
                                        0),
                                1)));
        textInputEditText13.perform(scrollTo(), replaceText(""));

//        ViewInteraction textInputEditText14 = onView(
//                allOf(withId(R.id.SizeKost),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.kota_layout),
//                                        0),
//                                1),
//                        isDisplayed()));
//        textInputEditText14.perform(closeSoftKeyboard());



        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.addKost), withText("ADD KOST"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.containerAddKost),
                                        0),
                                7)));
        materialButton5.perform(scrollTo(), click());
//
//        ViewInteraction textInputEditText15 = onView(
//                allOf(withId(R.id.SizeKost),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.kota_layout),
//                                        0),
//                                0)));
//        textInputEditText15.perform(scrollTo(), click());

        ViewInteraction textInputEditText16 = onView(
                allOf(withId(R.id.SizeKost),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.kota_layout),
                                        0),
                                1)));
        textInputEditText16.perform(scrollTo(), replaceText("500"), closeSoftKeyboard());

        ViewInteraction textInputEditText17 = onView(
                allOf(withId(R.id.sisa_kamar), withText("14"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.sisa_layout),
                                        0),
                                1)));
        textInputEditText17.perform(scrollTo(), replaceText(""));

//        ViewInteraction textInputEditText18 = onView(
//                allOf(withId(R.id.sisa_kamar),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.sisa_layout),
//                                        0),
//                                1),
//                        isDisplayed()));
//        textInputEditText18.perform(closeSoftKeyboard());



        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.addKost), withText("ADD KOST"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.containerAddKost),
                                        0),
                                7)));
        materialButton6.perform(scrollTo(), click());

//        ViewInteraction textInputEditText19 = onView(
//                allOf(withId(R.id.sisa_kamar),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.sisa_layout),
//                                        0),
//                                1)));
//        textInputEditText19.perform(scrollTo(), click());

        ViewInteraction textInputEditText20 = onView(
                allOf(withId(R.id.sisa_kamar),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.sisa_layout),
                                        0),
                                1)));
        textInputEditText20.perform(scrollTo(), replaceText("14"), closeSoftKeyboard());



        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.addKost), withText("ADD KOST"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.containerAddKost),
                                        0),
                                7)));
        materialButton7.perform(scrollTo(), click());
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
