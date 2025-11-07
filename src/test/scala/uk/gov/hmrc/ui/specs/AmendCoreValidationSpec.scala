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

class AmendCoreValidationSpec extends BaseSpec {

  lazy val registration      = Registration
  lazy val auth              = Auth
  lazy val amendRegistration = AmendRegistration

  Feature("Core Validation within Amend Registration journeys") {

    Scenario("Intermediary can add already active fixed establishments when amending their registration") {

      Given("the intermediary views the NETP registration")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "minimalAmend")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144881")

      When("the intermediary clicks change for Fixed establishments in other countries to add details in this section")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment\\?waypoints\\=change-your-registration"
      )
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      Then(
        "the intermediary can add tax details by EU Vat Number for one EU country that is already active in another member state"
      )
      registration.checkJourneyUrl("vat-registered-eu-country/1?waypoints=change-your-registration")
      registration.selectCountry("Malta")
      registration.checkJourneyUrl("trading-name-business-address/1?waypoints=change-your-registration")
      registration.enterFETradingName("Malta Company")
      registration.enterAddress("123 Street Name", "", "Town", "", "")
      registration.checkJourneyUrl("registration-tax-type/1?waypoints=change-your-registration")
      registration.answerRegistrationType("VAT number")
      registration.checkJourneyUrl("eu-vat-number/1?waypoints=change-your-registration")
      registration.enterAnswer("MT11122211")
      registration.checkJourneyUrl("check-tax-details/1?waypoints=change-your-registration")
      registration.continue()

      And(
        "the intermediary can add tax details by EU Tax ID for another EU country that is already active in another member state"
      )
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("vat-registered-eu-country/2?waypoints=change-your-registration")
      registration.selectCountry("Portugal")
      registration.checkJourneyUrl("trading-name-business-address/2?waypoints=change-your-registration")
      registration.enterFETradingName("Portugal Company")
      registration.enterAddress("1 Road Name", "Suburb", "City", "Region-Name", "")
      registration.checkJourneyUrl("registration-tax-type/2?waypoints=change-your-registration")
      registration.answerRegistrationType("Tax ID number")
      registration.checkJourneyUrl("eu-tax-identification-number/2?waypoints=change-your-registration")
      registration.enterAnswer("AA111222")
      registration.checkJourneyUrl("check-tax-details/2?waypoints=change-your-registration")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      And("the intermediary is on the change-your-registration page")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144881")

      When("the intermediary submits the registration")
      registration.clickSubmit()

      Then("the successful-amend page shows the correct information in the registration has been amended")
      registration.checkJourneyUrl("successful-amend")
      amendRegistration.checkAmendedAnswers("coreValidationFixedEstablishments")
    }

    Scenario("Intermediary can add quarantined fixed establishments when amending their registration") {

      Given("the intermediary views the NETP registration")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "minimalAmend")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144881")

      When("the intermediary clicks change for Fixed establishments in other countries to add details in this section")
      registration.selectChangeOrRemoveLink(
        "eu-fixed-establishment\\?waypoints\\=change-your-registration"
      )
      registration.checkJourneyUrl("eu-fixed-establishment?waypoints=change-your-registration")
      registration.answerRadioButton("yes")

      Then(
        "the intermediary can add tax details for one EU country by EU Vat Number that is quarantined in another member state"
      )
      registration.checkJourneyUrl("vat-registered-eu-country/1?waypoints=change-your-registration")
      registration.selectCountry("Malta")
      registration.checkJourneyUrl("trading-name-business-address/1?waypoints=change-your-registration")
      registration.enterFETradingName("Malta Company")
      registration.enterAddress("123 Street Name", "", "Town", "", "")
      registration.checkJourneyUrl("registration-tax-type/1?waypoints=change-your-registration")
      registration.answerRegistrationType("VAT number")
      registration.checkJourneyUrl("eu-vat-number/1?waypoints=change-your-registration")
      registration.enterAnswer("MT11122222")
      registration.checkJourneyUrl("check-tax-details/1?waypoints=change-your-registration")
      registration.continue()

      And(
        "the intermediary can add tax details for another EU country by EU Tax ID that is quarantined in another member state"
      )
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")
      registration.answerRadioButton("yes")
      registration.checkJourneyUrl("vat-registered-eu-country/2?waypoints=change-your-registration")
      registration.selectCountry("Portugal")
      registration.checkJourneyUrl("trading-name-business-address/2?waypoints=change-your-registration")
      registration.enterFETradingName("Portugal Company")
      registration.enterAddress("1 Road Name", "Suburb", "City", "Region-Name", "")
      registration.checkJourneyUrl("registration-tax-type/2?waypoints=change-your-registration")
      registration.answerRegistrationType("Tax ID number")
      registration.checkJourneyUrl("eu-tax-identification-number/2?waypoints=change-your-registration")
      registration.enterAnswer("AB111222")
      registration.checkJourneyUrl("check-tax-details/2?waypoints=change-your-registration")
      registration.continue()
      registration.checkJourneyUrl("add-tax-details?waypoints=change-your-registration")
      registration.answerRadioButton("no")

      And("the intermediary is on the change-your-registration page")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144881")

      When("the intermediary submits the registration")
      registration.clickSubmit()

      Then("the successful-amend page shows the correct information in the registration has been amended")
      registration.checkJourneyUrl("successful-amend")
      amendRegistration.checkAmendedAnswers("coreValidationFixedEstablishments")
    }

    Scenario(
      "Intermediary can amend an FTR to one that matches already registered scheme in amend registration journey"
    ) {

      Given("the intermediary views the NETP registration")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "nonUkBasedFtr")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144777")

      When("the intermediary clicks change for National tax number")
      registration.selectChangeOrRemoveLink(
        "client-tax-reference\\?waypoints\\=change-your-registration"
      )

      Then(
        "the intermediary can amend their client's national tax number to one that is already registered in another EU country"
      )
      registration.checkJourneyUrl("client-tax-reference?waypoints=change-your-registration")
      registration.enterAnswer("AB1122331")

      And("the intermediary is on the change-your-registration page")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144777")

      When("the intermediary submits the registration")
      registration.clickSubmit()

      Then("the successful-amend page shows the correct information in the registration has been amended")
      registration.checkJourneyUrl("successful-amend")
      // checks to be added in VEI-625/VEI-629
    }

    Scenario(
      "Intermediary can amend an FTR to one that matches a quarantined scheme in amend registration journey"
    ) {

      Given("the intermediary views the NETP registration")
      auth.goToAuthorityWizard()
      auth.loginUsingAuthorityWizard(true, true, "nonUkBasedFtr")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144777")

      When("the intermediary clicks change for National tax number")
      registration.selectChangeOrRemoveLink(
        "client-tax-reference\\?waypoints\\=change-your-registration"
      )

      Then(
        "the intermediary can amend their client's national tax number to one that is quarantined in another EU country"
      )
      registration.checkJourneyUrl("client-tax-reference?waypoints=change-your-registration")
      registration.enterAnswer("AB1122332")

      And("the intermediary is on the change-your-registration page")
      registration.checkJourneyUrl("change-your-registration")
      amendRegistration.checkIossNumber("IM9001144777")

      When("the intermediary submits the registration")
      registration.clickSubmit()

      Then("the successful-amend page shows the correct information in the registration has been amended")
      registration.checkJourneyUrl("successful-amend")
      // checks to be added in VEI-625/VEI-629
    }
  }
}
