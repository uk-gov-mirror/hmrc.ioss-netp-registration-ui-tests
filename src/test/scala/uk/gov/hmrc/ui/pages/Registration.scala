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

package uk.gov.hmrc.ui.pages

import org.openqa.selenium.{By, Keys}
import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait}
import org.scalatest.matchers.should.Matchers.*
import uk.gov.hmrc.configuration.TestEnvironment
import uk.gov.hmrc.selenium.webdriver.Driver
import org.junit.Assert
import uk.gov.hmrc.ui.pages.Auth.*

object Registration extends BasePage {

  private val registrationUrl: String =
    TestEnvironment.url("ioss-netp-registration-frontend")
  private val journeyUrl: String      = "/pay-clients-vat-on-eu-sales/register-new-ioss-client"
  private val dashboardUrl: String    = TestEnvironment.url(
    "ioss-intermediary-dashboard-frontend"
  ) + "/pay-clients-vat-on-eu-sales/manage-ioss-returns-payments-clients"

  var urlCode        = ""
  var activationCode = ""

  def goToRegistrationJourney(): Unit =
    get(registrationUrl + journeyUrl)

  def checkJourneyUrl(page: String): Unit =
    val url = s"$registrationUrl$journeyUrl/$page"
    fluentWait.until(ExpectedConditions.urlContains(url))
    getCurrentUrl should startWith(url)

  def goToPage(page: String): Unit =
    get(s"$registrationUrl$journeyUrl/$page")

  def answerRadioButton(answer: String): Unit = {

    answer match {
      case "yes" => click(By.id("value"))
      case "no"  => click(By.id("value-no"))
      case _     => throw new Exception("Option doesn't exist")
    }
    click(continueButton)
  }

  def enterAnswer(answer: String): Unit =
    sendKeys(By.id("value"), answer)
    click(continueButton)

  def enterAddress(
    line1: String,
    line2: String,
    townOrCity: String,
    stateOrRegion: String,
    postCode: String
  ): Unit =
    sendKeys(By.id("line1"), line1)
    sendKeys(By.id("line2"), line2)
    sendKeys(By.id("townOrCity"), townOrCity)
    sendKeys(By.id("stateOrRegion"), stateOrRegion)
    sendKeys(By.id("postCode"), postCode)
    continue()

  def continue(): Unit =
    click(continueButton)

  def waitForElement(by: By): Unit =
    new FluentWait(Driver.instance).until(ExpectedConditions.presenceOfElementLocated(by))

  def selectCountry(country: String): Unit = {
    val inputId = "value"
    sendKeys(By.id(inputId), country)
    waitForElement(By.id(inputId))
    click(By.cssSelector("li#value__option--0"))
    click(continueButton)
  }

  def clearCountry(): Unit = {
    val input = Driver.instance.findElement(By.id("value")).getAttribute("value")
    if (input != null) {
      for (n <- input)
        Driver.instance.findElement(By.id("value")).sendKeys(Keys.BACK_SPACE)
    }
  }

  def answerVatDetailsUkVrn(): Unit = {
    answerRadioButton("yes")
    checkJourneyUrl("client-has-vat-number")
    answerRadioButton("yes")
    checkJourneyUrl("client-vat-number")
    enterAnswer("GB111222333")
  }

  def answerVatDetailsRegistrationFailures(vrn: String): Unit = {
    answerRadioButton("yes")
    checkJourneyUrl("client-has-vat-number")
    answerRadioButton("yes")
    checkJourneyUrl("client-vat-number")
    enterAnswer(vrn)
  }

  def answerVatDetailsNonUk(): Unit = {
    answerRadioButton("no")
    checkJourneyUrl("client-has-vat-number")
    answerRadioButton("yes")
    checkJourneyUrl("client-vat-number")
    enterAnswer("GB123456789")
    checkJourneyUrl("client-country-based")
    selectCountry("Angola")
    checkJourneyUrl("client-business-name")
    enterAnswer("Business name")
    checkJourneyUrl("client-address")
    enterAddress("House Name", "Suburb", "City-Name", "", "12345")
  }

  def selectChangeOrRemoveLink(link: String): Unit =
    click(By.cssSelector(s"a[href*=$link]"))

  def updateField(id: String, text: String): Unit =
    sendKeys(By.id(id), text)

  def fillContactDetails(name: String, phone: String, email: String): Unit = {
    sendKeys(By.id("fullName"), name)
    sendKeys(By.id("telephoneNumber"), phone)
    sendKeys(By.id("emailAddress"), email)
    click(continueButton)
  }

  def selectCheckbox(): Unit = {
    click(By.id("declaration"))
    click(continueButton)
  }

  def selectNETPCheckbox(): Unit = {
    click(By.id("declaration"))
    clickSubmit()
  }

  def clickSubmit(): Unit =
    click(submitButton)

  def checkProblemPage(): Unit = {
    fluentWait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")))
    val h1 = Driver.instance.findElement(By.tagName("h1")).getText
    Assert.assertTrue(h1.equals("Sorry, there is a problem with the service"))
  }

  def answerSchemeType(answer: String): Unit = {
    answer match {
      case "OSS"  => click(By.id("value_0"))
      case "IOSS" => click(By.id("value_1"))
      case _      => throw new Exception("Option doesn't exist")
    }
    click(continueButton)
  }

  def enterFETradingName(tradingName: String): Unit =
    sendKeys(By.id("tradingName"), tradingName)

  def answerRegistrationType(answer: String): Unit = {
    answer match {
      case "VAT number"    => click(By.id("value_0"))
      case "Tax ID number" => click(By.id("value_1"))
      case _               => throw new Exception("Option doesn't exist")
    }
    click(continueButton)
  }

  def completeAndSubmitRegistrationCompulsoryAnswersOnly(): Unit = {
    completeRegistrationCompulsoryAnswersOnly()
    checkJourneyUrl("check-your-answers")
    continue()
    checkJourneyUrl("declaration")
    selectCheckbox()
    checkJourneyUrl("client-application-complete")
  }

  def completeRegistrationCompulsoryAnswersOnly(): Unit = {
    checkJourneyUrl("have-trading-name")
    answerRadioButton("no")
    checkJourneyUrl("previous-oss")
    answerRadioButton("no")
    checkJourneyUrl("eu-fixed-establishment")
    answerRadioButton("no")
    checkJourneyUrl("website-address/1")
    enterAnswer("www.1st-website.co.uk")
    checkJourneyUrl("add-website-address")
    answerRadioButton("no")
    checkJourneyUrl("business-contact-details")
    fillContactDetails("Firstname Surname", "+44123456789", "test-email@test.co.uk")
  }

  def enterOnePreviousRegistration(): Unit = {
    checkJourneyUrl("previous-country/1")
    selectCountry("Cyprus")
    checkJourneyUrl("previous-scheme/1/1")
    answerSchemeType("OSS")
    checkJourneyUrl("previous-oss-scheme-number/1/1")
    enterAnswer("EU111222333")
    checkJourneyUrl("previous-scheme-answers/1")
    answerRadioButton("yes")
    checkJourneyUrl("previous-scheme/1/2")
    answerSchemeType("IOSS")
    checkJourneyUrl("previous-scheme-intermediary/1/2")
    answerRadioButton("no")
    checkJourneyUrl("previous-ioss-number/1/2")
    enterAnswer("IM1967773331")
    checkJourneyUrl("previous-scheme-answers/1")
    answerRadioButton("no")
    checkJourneyUrl("previous-schemes-overview")
    answerRadioButton("no")
  }

  def enterOneEuTaxDetails(): Unit = {
    checkJourneyUrl("vat-registered-eu-country/1")
    selectCountry("Spain")
    checkJourneyUrl("trading-name-business-address/1")
    enterFETradingName("Spanish Trading Name")
    enterAddress("123 Street Name", "", "Town", "", "ES12345")
    checkJourneyUrl("registration-tax-type/1")
    answerRegistrationType("VAT number")
    checkJourneyUrl("eu-vat-number/1")
    enterAnswer("EST5554441B")
    checkJourneyUrl("check-tax-details/1")
    continue()
    checkJourneyUrl("add-tax-details")
    answerRadioButton("no")
  }

  def enterTwoEuDetails(): Unit = {
    checkJourneyUrl("vat-registered-eu-country/1")
    selectCountry("Spain")
    checkJourneyUrl("trading-name-business-address/1")
    enterFETradingName("Spanish Trading Name")
    enterAddress("123 Street Name", "", "Town", "", "ES12345")
    checkJourneyUrl("registration-tax-type/1")
    answerRegistrationType("VAT number")
    checkJourneyUrl("eu-vat-number/1")
    enterAnswer("EST5554441B")
    checkJourneyUrl("check-tax-details/1")
    continue()
    checkJourneyUrl("add-tax-details")
    answerRadioButton("yes")
    checkJourneyUrl("vat-registered-eu-country/2")
    selectCountry("Netherlands")
    checkJourneyUrl("trading-name-business-address/2")
    enterFETradingName("Netherlands Trading Name")
    enterAddress("1 Road Name", "Suburb", "City", "Region-Name", "NL5555 12")
    checkJourneyUrl("registration-tax-type/2")
    answerRegistrationType("Tax ID number")
    checkJourneyUrl("eu-tax-identification-number/2")
    enterAnswer("NL1 665544")
    checkJourneyUrl("check-tax-details/2")
    continue()
    checkJourneyUrl("add-tax-details")
    answerRadioButton("no")
  }

  def enterTwoPreviousRegistrations(): Unit = {
    checkJourneyUrl("previous-country/1")
    selectCountry("Cyprus")
    checkJourneyUrl("previous-scheme/1/1")
    answerSchemeType("OSS")
    checkJourneyUrl("previous-oss-scheme-number/1/1")
    enterAnswer("EU111222333")
    checkJourneyUrl("previous-scheme-answers/1")
    answerRadioButton("yes")
    checkJourneyUrl("previous-scheme/1/2")
    answerSchemeType("OSS")
    checkJourneyUrl("previous-oss-scheme-number/1/2")
    enterAnswer("CY44445555A")
    checkJourneyUrl("previous-scheme-answers/1")
    answerRadioButton("yes")
    checkJourneyUrl("previous-scheme/1/3")
    answerSchemeType("IOSS")
    checkJourneyUrl("previous-scheme-intermediary/1/3")
    answerRadioButton("yes")
    checkJourneyUrl("previous-ioss-number/1/3")
    enterAnswer("IM1967773331")
    checkJourneyUrl("previous-scheme-answers/1")
    continue()
    checkJourneyUrl("previous-schemes-overview")
    answerRadioButton("yes")
    checkJourneyUrl("previous-country/2")
    selectCountry("Poland")
    checkJourneyUrl("previous-scheme/2/1")
    answerSchemeType("IOSS")
    checkJourneyUrl("previous-scheme-intermediary/2/1")
    answerRadioButton("no")
    checkJourneyUrl("previous-ioss-number/2/1")
    enterAnswer("IM6167773331")
    checkJourneyUrl("previous-scheme-answers/2")
    answerRadioButton("no")
    checkJourneyUrl("previous-schemes-overview")
    answerRadioButton("no")
  }

  def setUrlCode(): Unit = {
    val htmlBody = Driver.instance.findElement(By.tagName("body")).getText
    urlCode = htmlBody.split("/")(6).substring(0, 6)
  }

  def getUrlCode(): String =
    urlCode

  def setActivationCode(): Unit = {
    val testOnlyUrl =
      s"http://localhost:10181/pay-clients-vat-on-eu-sales/register-new-ioss-client/test-only/get-client-code/$urlCode"
    get(testOnlyUrl)
    fluentWait.until(ExpectedConditions.urlContains(testOnlyUrl))

    val htmlBody = Driver.instance.findElement(By.tagName("body")).getText
    activationCode = htmlBody.split(">")(1).substring(0, 6)
  }

  def enterActivationCode(): Unit = {
    get(s"http://localhost:10181/pay-clients-vat-on-eu-sales/register-new-ioss-client/client-code-start/$urlCode")
    fluentWait.until(
      ExpectedConditions.urlContains(
        s"http://localhost:10181/pay-clients-vat-on-eu-sales/register-new-ioss-client/client-code-entry/$urlCode"
      )
    )

    sendKeys(By.id("value"), activationCode)
    click(continueButton)
  }

  def submitDeclarationAndRegistrationNETP(): Unit = {
    setUrlCode()
    goToAuthorityWizard()
    loginUsingAuthorityWizard(false, false, "noVrn")
    checkJourneyUrl("client-code-entry")
    setActivationCode()
    enterActivationCode()
    checkJourneyUrl("declaration-client")
    selectNETPCheckbox()
    checkJourneyUrl("successful-registration")
  }

  def expandDetails(): Unit =
    waitForElement(By.className("govuk-details__summary-text"))
    click(By.className("govuk-details__summary-text"))

  def clickResendCodeLink(): Unit =
    waitForElement(By.cssSelector(s"a[href*=resend-email\\/${getUrlCode()}]"))
    click(By.cssSelector(s"a[href*=resend-email\\/${getUrlCode()}]"))

  def checkHintText(): Unit =
    val htmlBody = Driver.instance.findElement(By.tagName("body")).getText
    Assert.assertTrue(htmlBody.contains("We have emailed your 6-digit code to"))

  def goToDashboard(): Unit =
    get(dashboardUrl)

  def checkDashboardJourneyUrl(page: String): Unit =
    val url = s"$dashboardUrl/$page"
    fluentWait.until(ExpectedConditions.urlContains(url))
    getCurrentUrl should startWith(url)

  def clickLink(link: String): Unit =
    click(By.id(link))

  def selectClientLink(link: String): Unit =
    click(By.cssSelector(s"a[href*=$link]"))

  def completeActivationCodePendingClient(): Unit = {
    get(
      "http://localhost:10181/pay-clients-vat-on-eu-sales/register-new-ioss-client/test-only/get-client-code/BRJRZF"
    )
    val htmlBody = Driver.instance.findElement(By.tagName("body")).getText
    activationCode = htmlBody.split(">")(1).substring(0, 6)

    get(
      "http://localhost:10181/pay-clients-vat-on-eu-sales/register-new-ioss-client/client-code-start/BRJRZF"
    )
    sendKeys(By.id("value"), activationCode)
    click(continueButton)
  }

  def checkSavedRegistrationsList(numberOfRegistered: String): Unit = {
    val htmlBody = Driver.instance.findElement(By.tagName("body")).getText

    if (numberOfRegistered == "five") {
      Assert.assertTrue(htmlBody.contains("First Company (VAT reference: 112233445)"))
      Assert.assertTrue(htmlBody.contains("NINO client (National Insurance Number: AA121212A)"))
      Assert.assertTrue(htmlBody.contains("UTR trading (tax reference: 1122331122333)"))
      Assert.assertTrue(htmlBody.contains("First Company (VAT reference: 544332211)"))
      Assert.assertTrue(htmlBody.contains("FTR trading (tax reference: 123MCDONALD456)"))
    } else if (numberOfRegistered == "four") {
      Assert.assertTrue(htmlBody.contains("First Company (VAT reference: 112233445)"))
      Assert.assertTrue(htmlBody.contains("NINO client (National Insurance Number: AA121212A)"))
      Assert.assertFalse(htmlBody.contains("UTR trading (tax reference: 1122331122333)"))
      Assert.assertTrue(htmlBody.contains("First Company (VAT reference: 544332211)"))
      Assert.assertTrue(htmlBody.contains("FTR trading (tax reference: 123MCDONALD456)"))
    }
  }

  def checkSavedRegistrationsHeading(numberOfRegistered: String): Unit = {
    fluentWait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")))
    val heading = Driver.instance.findElement(By.tagName("h1")).getText

    val textToCheck = numberOfRegistered match {
      case "oneSaved"                =>
        "Do you want to continue the registration for One saved registration trader (tax reference: AT123123123)"
      case "oneOfFive"               =>
        "Do you want to continue the registration for First Company (VAT reference: 112233445)"
      case "threeOfFive"             =>
        "Do you want to continue the registration for UTR trading (tax reference: 1122331122333)"
      case "fiveOfFive"              =>
        "Do you want to continue the registration for FTR trading (tax reference: 123MCDONALD456)"
      case "fiveSaved" | "fourSaved" =>
        "Which registration would you like to continue?"
      case _                         =>
        "No other matches available"
    }
    Assert.assertTrue(heading.equals(textToCheck))
  }

  def checkSavedRegistrationsLink(): Unit = {
    val htmlBody = Driver.instance.findElement(By.tagName("body")).getText
    Assert.assertFalse(htmlBody.contains("Continue a registration in progress"))
  }

  def goToSavedRegistrationJourney(): Unit =
    get(s"$registrationUrl$journeyUrl/clients-continue-registration-selection")

  def selectSavedRegistration(registration: String): Unit = {
    val radioButtonToSelect = registration match {
      case "first" | "only" => "0"
      case "second"         => "1"
      case "third"          => "2"
      case "fourth"         => "3"
      case "fifth"          => "4"
      case _                =>
        throw new Exception("Selection doesn't exist")
    }
    val buttonId            = s"value_$radioButtonToSelect"
    fluentWait.until(ExpectedConditions.presenceOfElementLocated(By.id(buttonId)))
    click(By.id(buttonId))
    click(continueButton)
  }

  def selectContinueRegistration(answer: String): Unit = {
    click(By.id(answer))
    click(continueButton)
  }

  def saveRegistration(): Unit =
    click(By.id("saveProgress"))

  def checkRegistrationUrl(): Unit = {
    val url = registrationUrl + journeyUrl
    fluentWait.until(ExpectedConditions.urlContains(url))
    getCurrentUrl should startWith(url)
  }

  def clickCancel(): Unit =
    click(By.id("cancel"))
}
