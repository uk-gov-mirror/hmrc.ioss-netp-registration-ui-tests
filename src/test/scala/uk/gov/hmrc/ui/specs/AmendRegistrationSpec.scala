/*
 * Copyright 2025 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ui.specs

import uk.gov.hmrc.ui.pages.{AmendRegistration, Auth, Registration}

class AmendRegistrationSpec extends BaseSpec {

  lazy val registration      = Registration
  lazy val auth              = Auth
  lazy val amendRegistration = AmendRegistration

  Feature("Amend Registration journeys") {

    Scenario(
      "Intermediary can view a NETP registration for a UK based client with UK VRN and cannot amend registration details section"
    ) {

      Given("the intermediary views the NETP registration")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "ukBasedUkVrn")
      registration.checkJourneyUrl("change-your-registration")

      Then("the correct Registration details are displayed for a UK based client with a UK VRN")
      amendRegistration.checkIossNumber("IM9001144771")
      amendRegistration.checkRegistrationDetails("ukBasedUkVrn")

      When("the intermediary submits the registration without amending any details")
      registration.clickSubmit()

      Then("the successful-amend page shows that no information in the registration has been amended")
      registration.checkJourneyUrl("successful-amend")
      amendRegistration.checkAmendedAnswers("noAmendedAnswers")
    }

    Scenario(
      "Intermediary can view a NETP registration for a UK based client with UTR and can amend registration details section"
    ) {

      Given("the intermediary views the NETP registration")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "ukBasedUtr")
      registration.checkJourneyUrl("change-your-registration")

      Then("the correct Registration details are displayed for a UK based client with a UTR")
      amendRegistration.checkIossNumber("IM9001144773")
      amendRegistration.checkRegistrationDetails("ukBasedUtr")

      When("the intermediary clicks change for Trading name")
      registration.selectChangeOrRemoveLink(
        "client-business-name\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can amend their client's business name")
      registration.checkJourneyUrl("client-business-name?waypoints=change-your-registration")
      registration.enterAnswer("Updated 3rd client name")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144773")

      When("the intermediary clicks change for Principal place of business address")
      registration.selectChangeOrRemoveLink(
        "client-address\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can amend their client's business address")
      registration.checkJourneyUrl("client-address?waypoints=change-your-registration")
      registration.updateField("line1", "1 New Address Line")
      registration.updateField("townOrCity", "New Town-Name")
      registration.updateField("postCode", "")
      registration.continue()
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144773")

      Then("the Registration details are correct following the amendment")
      amendRegistration.checkRegistrationDetails("ukBasedUtrAmended")

      When("the intermediary submits the registration")
      registration.clickSubmit()

      Then("the successful-amend page shows the correct amendments to the registration")
      registration.checkJourneyUrl("successful-amend")
      // checks to be added in VEI-625/VEI-629
    }

    Scenario(
      "Intermediary can view a NETP registration for a UK based client with NINO and can amend registration details section"
    ) {

      Given("the intermediary views the NETP registration")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "ukBasedNino")
      registration.checkJourneyUrl("change-your-registration")

      Then("the correct Registration details are displayed for a UK based client with a NINO")
      amendRegistration.checkIossNumber("IM9001144778")
      amendRegistration.checkRegistrationDetails("ukBasedNino")

      When("the intermediary clicks change for Trading name")
      registration.selectChangeOrRemoveLink(
        "client-business-name\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can amend their client's business name")
      registration.checkJourneyUrl("client-business-name?waypoints=change-your-registration")
      registration.enterAnswer("Updated 8th client name")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144778")

      When("the intermediary clicks change for Principal place of business address")
      registration.selectChangeOrRemoveLink(
        "client-address\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can amend their client's business address")
      registration.checkJourneyUrl("client-address?waypoints=change-your-registration")
      registration.updateField("line2", "New Suburb")
      registration.updateField("stateOrRegion", "")
      registration.continue()
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144778")

      Then("the Registration details are correct following the amendment")
      amendRegistration.checkRegistrationDetails("ukBasedNinoAmended")

      When("the intermediary submits the registration")
      registration.clickSubmit()

      Then("the successful-amend page shows the correct amendments to the registration")
      registration.checkJourneyUrl("successful-amend")
      // checks to be added in VEI-625/VEI-629
    }

    Scenario(
      "Intermediary can view a NETP registration for a Non-UK based client with UK VRN and can amend registration details section"
    ) {

      Given("the intermediary views the NETP registration")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "nonUkBasedUkVrn")
      registration.checkJourneyUrl("change-your-registration")

      Then("the correct Registration details are displayed for a Non-UK based client with a UK VRN")
      amendRegistration.checkIossNumber("IM9001144775")
      amendRegistration.checkRegistrationDetails("nonUkBasedUkVrn")

      When("the intermediary clicks change for Country based in")
      registration.selectChangeOrRemoveLink(
        "client-country-based\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary amends the country the client is based in")
      registration.checkJourneyUrl("client-country-based?waypoints=change-your-registration")
      registration.clearCountry()
      registration.selectCountry("Christmas Island")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144775")

      When("the intermediary clicks change for Trading name")
      registration.selectChangeOrRemoveLink(
        "client-business-name\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can amend their client's business name")
      registration.checkJourneyUrl("client-business-name?waypoints=change-your-registration")
      registration.enterAnswer("New 5th client name")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144775")

      When("the intermediary clicks change for Principal place of business address")
      registration.selectChangeOrRemoveLink(
        "client-address\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can amend their client's business address")
      registration.checkJourneyUrl("client-address?waypoints=change-your-registration")
      registration.updateField("line2", "New Suburb")
      registration.updateField("stateOrRegion", "State")
      registration.updateField("postCode", "")
      registration.continue()
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144775")

      Then("the Registration details are correct following the amendment")
      amendRegistration.checkRegistrationDetails("nonUkBasedUkVrnAmended")

      When("the intermediary submits the registration")
      registration.clickSubmit()

      Then("the successful-amend page shows the correct amendments to the registration")
      registration.checkJourneyUrl("successful-amend")
      // checks to be added in VEI-625/VEI-629
    }

    Scenario(
      "Intermediary can view a NETP registration for a Non-UK based client with FTR and can amend registration details section"
    ) {

      Given("the intermediary views the NETP registration")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "nonUkBasedFtr")
      registration.checkJourneyUrl("change-your-registration")

      Then("the correct Registration details are displayed for a Non-UK based client with an FTR")
      amendRegistration.checkIossNumber("IM9001144777")
      amendRegistration.checkRegistrationDetails("nonUkBasedFtr")

      When("the intermediary clicks change for Country based in")
      registration.selectChangeOrRemoveLink(
        "client-country-based\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary amends the country the client is based in")
      registration.checkJourneyUrl("client-country-based?waypoints=change-your-registration")
      registration.clearCountry()
      registration.selectCountry("Canada")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144777")

      When("the intermediary clicks change for Trading name")
      registration.selectChangeOrRemoveLink(
        "client-business-name\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can amend their client's business name")
      registration.checkJourneyUrl("client-business-name?waypoints=change-your-registration")
      registration.enterAnswer("New 7th client name")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144777")

      When("the intermediary clicks change for National tax number")
      registration.selectChangeOrRemoveLink(
        "client-tax-reference\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can amend their client's national tax number")
      registration.checkJourneyUrl("client-tax-reference?waypoints=change-your-registration")
      registration.enterAnswer("CA112233")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144777")

      When("the intermediary clicks change for Principal place of business address")
      registration.selectChangeOrRemoveLink(
        "client-address\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can amend their client's business address")
      registration.checkJourneyUrl("client-address?waypoints=change-your-registration")
      registration.updateField("line1", "200 Street Name")
      registration.updateField("postCode", "")
      registration.continue()
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144777")

      Then("the Registration details are correct following the amendment")
      amendRegistration.checkRegistrationDetails("nonUkBasedFtrAmended")

      When("the intermediary submits the registration")
      registration.clickSubmit()

      Then("the successful-amend page shows the correct amendments to the registration")
      registration.checkJourneyUrl("successful-amend")
      // checks to be added in VEI-625/VEI-629
    }

    Scenario("Intermediary can amend contact details in a NETP registration") {

      Given("the intermediary accesses the IOSS NETP Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "amend")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144771")

      When("the intermediary clicks change for contact details")
      registration.selectChangeOrRemoveLink(
        "business-contact-details\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can update their phone number and email address")
      registration.checkJourneyUrl("business-contact-details?waypoints=change-your-registration")
      registration.updateField("telephoneNumber", "+441234567890")
      registration.updateField("emailAddress", "amend-test@email.com")
      registration.continue()

      And("the intermediary is on the change-your-registration page")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144771")

      When("the intermediary submits the registration without amending any details")
      registration.clickSubmit()

      Then("the successful-amend page shows the correct amendments to the registration")
      registration.checkJourneyUrl("successful-amend")
      amendRegistration.checkAmendedAnswers("contactDetails")
    }

    Scenario("Intermediary can amend and remove existing data in a NETP registration") {

      Given("the intermediary accesses the IOSS NETP Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "amend")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144771")

      When("the intermediary clicks change for Other trading names")
      registration.selectChangeOrRemoveLink(
        "add-trading-name\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can amend the second trading name")
      registration.checkJourneyUrl("add-trading-name?waypoints=change-your-registration")
      registration.selectChangeOrRemoveLink(
        "trading-name\\/2\\?waypoints\\=change-add-trading-name\\%2Cchange-your-registration"
      )
      registration.checkJourneyUrl("trading-name/2?waypoints=change-add-trading-name%2Cchange-your-registration")
      registration.enterAnswer("new trading name")
      registration.checkJourneyUrl("add-trading-name?waypoints=change-your-registration")

      And("the intermediary can remove the first trading name")
      registration.selectChangeOrRemoveLink(
        "remove-trading-name\\/1\\?waypoints\\=change-your-registration"
      )
      registration.checkJourneyUrl("remove-trading-name/1?waypoints=change-your-registration")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("add-trading-name?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      When("the intermediary clicks change for Countries established in")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144771")
      registration.selectChangeOrRemoveLink(
        "add-tax-details\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can amend an existing fixed establishment")
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")
      registration.selectChangeOrRemoveLink(
        "check-tax-details\\/1\\?waypoints\\=change-your-registration"
      )
      registration.checkJourneyUrl("check-tax-details/1?waypoints=change-your-registration")
      registration.selectChangeOrRemoveLink(
        "registration-tax-type\\/1\\?waypoints\\=check-tax-details-1\\%2Cchange-your-registration"
      )
      registration.checkJourneyUrl(
        "registration-tax-type/1?waypoints=check-tax-details-1%2Cchange-your-registration"
      )
      registration.answerRegistrationType("Tax ID number")
      registration.checkJourneyUrl(
        "eu-tax-identification-number/1?waypoints=check-tax-details-1%2Cchange-your-registration"
      )
      registration.enterAnswer("NEW123")
      registration.checkJourneyUrl("check-tax-details/1?waypoints=change-your-registration")
      registration.continue()

      Then("the intermediary can remove an existing fixed establishment")
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")
      registration.selectChangeOrRemoveLink(
        "remove-tax-details\\/2\\?waypoints\\=change-your-registration"
      )
      registration.checkJourneyUrl(
        "remove-tax-details/2?waypoints=change-your-registration"
      )
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      And("the intermediary is on the change-your-registration page")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144771")

      When("the intermediary submits the registration without amending any details")
      registration.clickSubmit()

      Then("the successful-amend page shows the correct amendments to the registration")
      registration.checkJourneyUrl("successful-amend")
      amendRegistration.checkAmendedAnswers("editExisting")
    }

    Scenario("Intermediary can remove all trading names and fixed establishments in a NETP registration - yes to no") {

      Given("the intermediary accesses the IOSS NETP Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "ukBasedUkVrn")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144771")

      When("the intermediary clicks change for Have a different trading name")
      registration.selectChangeOrRemoveLink(
        "have-trading-name\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary answers no on the have-trading-name page")
      registration.checkJourneyUrl("have-trading-name?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      And("the intermediary answers yes on the remove-all-trading-names page")
      registration.checkJourneyUrl("remove-all-trading-names?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      When("the intermediary clicks change for Fixed establishments in other countries")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144771")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary answers no on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      And("the intermediary answers yes on the remove-all-tax-details page")
      registration.checkJourneyUrl("remove-all-tax-details?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      And("the intermediary is on the change-your-registration page")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144771")

      When("the intermediary submits the registration without amending any details")
      registration.clickSubmit()

      Then("the successful-amend page shows the correct amendments to the registration")
      registration.checkJourneyUrl("successful-amend")
      amendRegistration.checkAmendedAnswers("yesToNo")
    }

    Scenario("Intermediary can add fresh data to a NETP registration - no to yes") {

      Given("the intermediary accesses the IOSS NETP Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "minimalAmend")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144881")

      When("the intermediary clicks change for Have a different trading name")
      registration.selectChangeOrRemoveLink(
        "have-trading-name\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary adds two trading names")
      registration.checkJourneyUrl("have-trading-name?waypoints=change-your-registration")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("trading-name/1?waypoints=add-trading-name%2Cchange-your-registration")
      registration.enterAnswer("amend trading name 1")
      registration.checkJourneyUrl("add-trading-name?waypoints=change-your-registration")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("trading-name/2?waypoints=add-trading-name%2Cchange-your-registration")
      registration.enterAnswer("amend trading name 2")
      registration.checkJourneyUrl("add-trading-name?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      When("the intermediary clicks change for Other One Stop Shop registrations")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144881")
      registration.selectChangeOrRemoveLink(
        "previous-oss\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary answers yes on the previous-oss page")
      registration.checkJourneyUrl("previous-oss?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      And("the intermediary adds a previous oss registration for Luxembourg")
      registration.checkJourneyUrl("previous-country/1?waypoints=change-your-registration")
      registration.selectCountry("Luxembourg")
      registration.checkJourneyUrl("previous-scheme/1/1?waypoints=previous-scheme-answers-1%2Cchange-your-registration")
      registration.answerSchemeType("OSS")
      registration.checkJourneyUrl(
        "previous-oss-scheme-number/1/1?waypoints=previous-scheme-answers-1%2Cchange-your-registration"
      )
      registration.enterAnswer("LU12345678")
      registration.checkJourneyUrl("previous-scheme-answers/1?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      And("the intermediary adds a previous ioss registration for Luxembourg")
      registration.checkJourneyUrl("previous-scheme/1/2?waypoints=previous-scheme-answers-1%2Cchange-your-registration")
      registration.answerSchemeType("IOSS")
      registration.checkJourneyUrl(
        "previous-scheme-intermediary/1/2?waypoints=previous-scheme-answers-1%2Cchange-your-registration"
      )
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl(
        "previous-ioss-number/1/2?waypoints=previous-scheme-answers-1%2Cchange-your-registration"
      )
      registration.enterAnswer("IM4427777777")
      registration.checkJourneyUrl("previous-scheme-answers/1?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      And("the intermediary adds a previous non-union oss registration for Bulgaria")
      registration.checkJourneyUrl("previous-schemes-overview?waypoints=change-your-registration")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl(
        "previous-country/2?waypoints=change-previous-schemes-overview%2Cchange-your-registration"
      )
      registration.selectCountry("Bulgaria")
      registration.checkJourneyUrl(
        "previous-scheme/2/1?waypoints=previous-scheme-answers-2%2Cchange-previous-schemes-overview%2Cchange-your-registration"
      )
      registration.answerSchemeType("OSS")
      registration.checkJourneyUrl(
        "previous-oss-scheme-number/2/1?waypoints=previous-scheme-answers-2%2Cchange-previous-schemes-overview%2Cchange-your-registration"
      )
      registration.enterAnswer("EU123456789")
      registration.checkJourneyUrl(
        "previous-scheme-answers/2?waypoints=change-previous-schemes-overview%2Cchange-your-registration"
      )
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("previous-schemes-overview?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      When("the intermediary clicks change for Fixed establishments in other countries")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144881")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary answers yes on the eu-fixed-establishment page")
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      And(
        "the intermediary selects which country their fixed establishment is in on the vat-registered-eu-country page"
      )
      registration.checkJourneyUrl("vat-registered-eu-country/1?waypoints=change-your-registration")
      registration.selectCountry("Sweden")

      And("the intermediary enters the fixed establishment details on the trading-name-business-address page")
      registration.checkJourneyUrl("trading-name-business-address/1?waypoints=change-your-registration")
      registration.enterFETradingName("Swedish Company")
      registration.enterAddress("123 Street Name", "", "Town", "", "SE123456")

      And("the intermediary selects the VAT Number registration type on the registration-tax-type page")
      registration.checkJourneyUrl("registration-tax-type/1?waypoints=change-your-registration")
      registration.answerRegistrationType("VAT number")

      And("the intermediary enters the VAT number on the eu-vat-number page")
      registration.checkJourneyUrl("eu-vat-number/1?waypoints=change-your-registration")
      registration.enterAnswer("SE012345678987")

      And("the intermediary continues through the check-tax-details page")
      registration.checkJourneyUrl("check-tax-details/1?waypoints=change-your-registration")
      registration.continue()

      Then("the intermediary selects yes on the add-tax-details page")
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      And(
        "the intermediary selects which country their fixed establishment is in on the vat-registered-eu-country page"
      )
      registration.checkJourneyUrl("vat-registered-eu-country/2?waypoints=change-your-registration")
      registration.selectCountry("Romania")

      And("the intermediary enters the fixed establishment details on the trading-name-business-address page")
      registration.checkJourneyUrl("trading-name-business-address/2?waypoints=change-your-registration")
      registration.enterFETradingName("Romanian Company")
      registration.enterAddress("1 Road Name", "Suburb", "City", "Region-Name", "")

      And("the intermediary selects the Tax ID number registration type on the registration-tax-type page")
      registration.checkJourneyUrl("registration-tax-type/2?waypoints=change-your-registration")
      registration.answerRegistrationType("Tax ID number")

      And("the intermediary enters the Tax ID number on the eu-tax-identification-number page")
      registration.checkJourneyUrl("eu-tax-identification-number/2?waypoints=change-your-registration")
      registration.enterAnswer("R1 665544")

      And("the intermediary continues through the check-tax-details page")
      registration.checkJourneyUrl("check-tax-details/2?waypoints=change-your-registration")
      registration.continue()

      Then("the intermediary selects no on the add-tax-details page")
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      And("the intermediary is on the change-your-registration page")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144881")

      When("the intermediary submits the registration without amending any details")
      registration.clickSubmit()

      Then("the successful-amend page shows the correct amendments to the registration")
      registration.checkJourneyUrl("successful-amend")
      amendRegistration.checkAmendedAnswers("noToYes")
    }

    Scenario("Intermediary can amend existing website details in a NETP registration") {

      Given("the intermediary accesses the IOSS NETP Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "minimalAmend")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144881")

      When("the intermediary clicks change for Trading websites")
      registration.selectChangeOrRemoveLink(
        "add-website-address\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can update their second website")
      registration.checkJourneyUrl("add-website-address?waypoints=change-your-registration")
      registration.selectChangeOrRemoveLink(
        "website-address\\/2\\?waypoints\\=change-add-website-address\\%2Cchange-your-registration"
      )
      registration.checkJourneyUrl("website-address/2?waypoints=change-add-website-address%2Cchange-your-registration")
      registration.enterAnswer("https://updatedwebsite.co")
      registration.checkJourneyUrl("add-website-address?waypoints=change-your-registration")

      And("the intermediary can remove their first website")
      registration.selectChangeOrRemoveLink(
        "remove-website-address\\/1\\?waypoints\\=change-your-registration"
      )
      registration.checkJourneyUrl("remove-website-address/1?waypoints=change-your-registration")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("add-website-address?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      And("the intermediary is on the change-your-registration page")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144881")

      When("the intermediary submits the registration without amending any details")
      registration.clickSubmit()

      Then("the successful-amend page shows the correct amendments to the registration")
      registration.checkJourneyUrl("successful-amend")
      amendRegistration.checkAmendedAnswers("websites")
    }

    Scenario(
      "Intermediary cannot access the remove all previous registrations functionality during an amend journey"
    ) {
      Given("the intermediary accesses the IOSS NETP Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "amend")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144771")

      When("the intermediary attempts to access the remove-all-previous-registrations in amend journey")
      registration.goToPage("remove-all-previous-registrations?waypoints=change-your-registration")

      Then("the intermediary is shown the Sorry, there is a problem with the service page")
      registration.checkJourneyUrl("remove-all-previous-registrations?waypoints=change-your-registration")
      registration.checkProblemPage()
    }

    Scenario(
      "Intermediary can add and remove new previous registrations but cannot remove existing previous registrations"
    ) {

      Given("the intermediary accesses the IOSS NETP Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "amend")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144771")

      When(
        "the intermediary is shown the correct links for previous registrations on the change-your-registration page"
      )
      amendRegistration.checkPreviousRegistrationLinks("preAmendCYA")

      Then("the intermediary selects Add for Countries registered in")
      registration.selectChangeOrRemoveLink("previous-schemes-overview\\?waypoints\\=change-your-registration")

      And(
        "the intermediary is shown the correct links for previous registrations on the previous-schemes-overview page"
      )
      registration.checkJourneyUrl("previous-schemes-overview?waypoints=change-your-registration")
      amendRegistration.checkPreviousRegistrationLinks("preAmendPSO")

      And("the intermediary selects Change for Germany")
      registration.selectChangeOrRemoveLink(
        "previous-scheme-answers\\/1\\?waypoints\\=change-previous-schemes-overview\\%2Cchange-your-registration"
      )

      And("the intermediary cannot remove their existing One Stop Shop Union registration")
      registration.checkJourneyUrl(
        "previous-scheme-answers/1?waypoints=change-previous-schemes-overview%2Cchange-your-registration"
      )
      amendRegistration.checkPreviousRegistrationLinks("preAmendNoRemove")

      And("the intermediary adds an IOSS scheme for Germany")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl(
        "previous-scheme/1/2?waypoints=previous-scheme-answers-1%2Cchange-previous-schemes-overview%2Cchange-your-registration"
      )
      registration.answerSchemeType("IOSS")
      registration.checkJourneyUrl(
        "previous-scheme-intermediary/1/2?waypoints=previous-scheme-answers-1%2Cchange-previous-schemes-overview%2Cchange-your-registration"
      )
      registration.answerRadioButton("no")
      registration.checkJourneyUrl(
        "previous-ioss-number/1/2?waypoints=previous-scheme-answers-1%2Cchange-previous-schemes-overview%2Cchange-your-registration"
      )
      registration.enterAnswer("IM2767777777")
      registration.checkJourneyUrl(
        "previous-scheme-answers/1?waypoints=change-previous-schemes-overview%2Cchange-your-registration"
      )

      And("the intermediary adds a non-union scheme for Germany")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl(
        "previous-scheme/1/3?waypoints=previous-scheme-answers-1%2Cchange-previous-schemes-overview%2Cchange-your-registration"
      )
      registration.answerSchemeType("OSS")
      registration.checkJourneyUrl(
        "previous-oss-scheme-number/1/3?waypoints=previous-scheme-answers-1%2Cchange-previous-schemes-overview%2Cchange-your-registration"
      )
      registration.enterAnswer("EU123456789")
      registration.checkJourneyUrl(
        "previous-scheme-answers/1?waypoints=change-previous-schemes-overview%2Cchange-your-registration"
      )

      And("the intermediary only has remove links for the newly added schemes during this amend journey")
      amendRegistration.checkPreviousRegistrationLinks("twoRemoveLinks")

      Then("the intermediary can remove the One Stop Shop non-Union scheme")
      registration.selectChangeOrRemoveLink(
        "remove-previous-scheme\\/1\\/3\\?waypoints\\=change-previous-schemes-overview\\%2Cchange-your-registration"
      )
      registration.checkJourneyUrl(
        "remove-previous-scheme/1/3?waypoints=change-previous-schemes-overview%2Cchange-your-registration"
      )
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl(
        "previous-scheme-answers/1?waypoints=change-previous-schemes-overview%2Cchange-your-registration"
      )
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("previous-schemes-overview?waypoints=change-your-registration")

      When("the intermediary adds a previous registration for France")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl(
        "previous-country/2?waypoints=change-previous-schemes-overview%2Cchange-your-registration"
      )
      registration.selectCountry("France")
      registration.checkJourneyUrl(
        "previous-scheme/2/1?waypoints=previous-scheme-answers-2%2Cchange-previous-schemes-overview%2Cchange-your-registration"
      )
      registration.answerSchemeType("IOSS")
      registration.checkJourneyUrl(
        "previous-scheme-intermediary/2/1?waypoints=previous-scheme-answers-2%2Cchange-previous-schemes-overview%2Cchange-your-registration"
      )
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl(
        "previous-ioss-number/2/1?waypoints=previous-scheme-answers-2%2Cchange-previous-schemes-overview%2Cchange-your-registration"
      )
      registration.enterAnswer("IM2507777777")
      registration.checkJourneyUrl(
        "previous-scheme-answers/2?waypoints=change-previous-schemes-overview%2Cchange-your-registration"
      )
      registration.answerRadioButton("no")
      registration.checkJourneyUrl("previous-schemes-overview?waypoints=change-your-registration")

      Then("the intermediary only has the option to remove France from the previous-schemes-overview page")
      amendRegistration.checkPreviousRegistrationLinks("onlyRemoveFrance")

      And("the intermediary answers no on the previous-schemes-overview page")
      registration.answerRadioButton("no")

      And("the intermediary is on the change-your-registration page")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144771")

      When("the intermediary submits the registration without amending any details")
      registration.clickSubmit()

      Then("the successful-amend page shows the correct amendments to the registration")
      registration.checkJourneyUrl("successful-amend")
      amendRegistration.checkAmendedAnswers("previousRegistrations")
    }

    Scenario("Intermediary can amend details in their registration then cancel the amendments without submitting") {

      Given("the intermediary accesses the IOSS NETP Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "minimalAmend")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144881")

      When("the intermediary clicks change for Trading websites")
      registration.selectChangeOrRemoveLink(
        "add-website-address\\?waypoints\\=change-your-registration"
      )

      Then("the intermediary can update their second website")
      registration.checkJourneyUrl("add-website-address?waypoints=change-your-registration")
      registration.selectChangeOrRemoveLink(
        "website-address\\/2\\?waypoints\\=change-add-website-address\\%2Cchange-your-registration"
      )
      registration.checkJourneyUrl("website-address/2?waypoints=change-add-website-address%2Cchange-your-registration")
      registration.enterAnswer("https://updatedwebsite.co")
      registration.checkJourneyUrl("add-website-address?waypoints=change-your-registration")

      And("the intermediary can remove their first website")
      registration.selectChangeOrRemoveLink(
        "remove-website-address\\/1\\?waypoints\\=change-your-registration"
      )
      registration.checkJourneyUrl("remove-website-address/1?waypoints=change-your-registration")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("add-website-address?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      And("the intermediary is on the change-your-registration page")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144881")

      // cancel not currently working - bug
      When("the intermediary clicks cancel")
      registration.clickCancel()

      And("the intermediary selects yes on the cancel-amend-registration page")
      registration.checkJourneyUrl("cancel-amend-registration")
      registration.answerRadioButton("yes")

      Then("the intermediary is redirected to the Intermediary dashboard service")
      registration.checkDashboardJourneyUrl("your-account")
    }

    Scenario("Failure from ETMP when Intermediary submits amended registration for NETP") {

      Given("the intermediary accesses the IOSS NETP Registration Service")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "failureAmend")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9002222222")

      When("the intermediary submits the registration without amending any details")
      registration.clickSubmit()

      Then("the submission failure page is displayed")
//      not currently being displayed - bug
    }
  }
}
