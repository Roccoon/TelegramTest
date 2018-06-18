import java.net.URLEncoder

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString
import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api.declarative.{Callbacks, Commands}
import info.mukel.telegrambot4s.api.{ChatActions, Polling, TelegramBot}
import info.mukel.telegrambot4s.methods.SendPhoto
import info.mukel.telegrambot4s.models.{InlineKeyboardButton, InlineKeyboardMarkup, InputFile}
/**
  *
  * https://www.google.ru/imgres?imgurl=https%3A%2F%2Fwww.vl.ru%2Fafisha%2Fuploads%2Fevents%2Fbed%2F61918_big.jpeg%3Fv%3D1492769770&imgrefurl=https%3A%2F%2Fwww.vl.ru%2Fafisha%2Fvladivostok%2Fevent%2F61918&docid=4Eo08oeAjj7cBM&tbnid=58-RevT3xadG5M%3A&vet=10ahUKEwiPlYqF3tvbAhVEfbwKHXtSAPsQMwhBKAcwBw..i&w=663&h=450&bih=864&biw=1328&q=%D0%BC%D0%B8%D1%85%D0%B0%D0%B8%D0%BB%20%D0%B5%D1%88%D1%82%D0%BE%D0%BA%D0%B8%D0%BD&ved=0ahUKEwiPlYqF3tvbAhVEfbwKHXtSAPsQMwhBKAcwBw&iact=mrc&uact=8
  * Show how to use callbacks, and it's shortcomings.
  *
  * @param token Bot's token.
  */
class ThatYouKnowMBot(val token: String) extends TelegramBot
  with Polling
  with Commands
  with ChatActions
  with Callbacks {

  val TAG11 = "COUNTER_TAG11"
  val TAG12 = "COUNTER_TAG12"
  val TAG21 = "COUNTER_TAG21"
  val TAG22 = "COUNTER_TAG22"
  val TAG31 = "COUNTER_TAG31"
  val TAG32 = "COUNTER_TAG32"
  val TAG33 = "COUNTER_TAG33"
  val TAG41 = "COUNTER_TAG41"
  val TAG42 = "COUNTER_TAG42"
  val TAG51 = "COUNTER_TAG51"
  val TAG52 = "COUNTER_TAG52"
  var count = 0

  onCommand("/start") { implicit msg =>
    println("s")
    reply("Опрос дня: Как хорошо вы знаете Мишутку?")
    withArgs { args =>
      val url = "https://scontent-hkg3-2.xx.fbcdn.net/v/t1.0-1/p160x160/18582241_106333229956405_4265884717937183258_n.jpg?_nc_cat=0&oh=af7a14f547eaf29ee6ba64943b761055&oe=5BBE50E3"
      for {
        response <- Http().singleRequest(HttpRequest(uri = Uri(url)))
        if response.status.isSuccess()
        bytes <- Unmarshal(response).to[ByteString]
      } /* do */ {
        val photo = InputFile("qrcode.png", bytes)
        uploadingPhoto // Hint the user
        request(SendPhoto(msg.source, photo))
      }
      Thread.sleep(3000)
      reply("Где Живет Мишутка?", replyMarkup = question1())
    }
  }


def question1():InlineKeyboardMarkup={
  val button1=InlineKeyboardButton.callbackData("В Караганде",TAG11)
  val button2=InlineKeyboardButton.callbackData("В Москве",TAG12)
  val twoButton=Seq(button1, button2)
  InlineKeyboardMarkup.singleRow(twoButton)
}
  def question2():InlineKeyboardMarkup={
    val button1=InlineKeyboardButton.callbackData("27",TAG21)
    val button2=InlineKeyboardButton.callbackData("25",TAG22)
    val twoButton=Seq(button1, button2)
    InlineKeyboardMarkup.singleRow(twoButton)
  }
  def question3():InlineKeyboardMarkup={
    val button1=InlineKeyboardButton.callbackData("Пирожок",TAG31)
    val button2=InlineKeyboardButton.callbackData("Цума",TAG32)
    val button3=InlineKeyboardButton.callbackData("У Мишутки нет собаки",TAG33)

    val twoButton=Seq(button1, button2,button3)
    InlineKeyboardMarkup.singleColumn(twoButton)
  }
  def question4():InlineKeyboardMarkup={
    val button1=InlineKeyboardButton.callbackData("МГТУ им. Баумана",TAG41)
    val button2=InlineKeyboardButton.callbackData("Кулинарый Техникум города Климовск",TAG42)
    val twoButton=Seq(button1, button2)
    InlineKeyboardMarkup.singleColumn(twoButton)
  }
  def question5():InlineKeyboardMarkup={
    val button1=InlineKeyboardButton.callbackData("0",TAG51)
    val button2=InlineKeyboardButton.callbackData("2",TAG52)
    val twoButton=Seq(button1, button2)
    InlineKeyboardMarkup.singleRow(twoButton)
  }



  onCallbackWithTag(TAG11) { implicit cbq1 =>
    // Notification only shown to the user who pressed the button.
    ackCallback(cbq1.from.firstName + " pressed the button!")
    // Or just ackCallback()
     implicit val c1 = cbq1.message.get
    reply("Возможно, но мы говорим про Мишутку на фото выше.\n\nСколько Мишутки Лет?",replyMarkup = question2())
  }

    onCallbackWithTag(TAG12) { implicit cbq =>
      // Notification only shown to the user who pressed the button.
      ackCallback(cbq.from.firstName + " pressed the button!")
      // Or just ackCallback()
      implicit val c2= cbq.message.get
      count+=1
      reply("И это правильно!\n\nСколько Мишутке лет?",replyMarkup = question2())
  }
  onCallbackWithTag(TAG21) { implicit cbq1 =>
    // Notification only shown to the user who pressed the button.
    ackCallback(cbq1.from.firstName + " pressed the button!")
    // Or just ackCallback()
    implicit val c1 = cbq1.message.get
    count+=1
    reply("И это правильно!\n\nКак зовут Мишуткину собаку?",replyMarkup = question3())
  }
  onCallbackWithTag(TAG22) { implicit cbq1 =>
    // Notification only shown to the user who pressed the button.
    ackCallback(cbq1.from.firstName + " pressed the button!")
    // Or just ackCallback()
    implicit val c1 = cbq1.message.get
    reply("Вы льстите Мишутке, он уже не так уж и свеж!\n\nКак зовут Мишуткину собаку??",replyMarkup = question3())
  }
  onCallbackWithTag(TAG31) { implicit cbq1 =>
    // Notification only shown to the user who pressed the button.
    ackCallback(cbq1.from.firstName + " pressed the button!")
    // Or just ackCallback()
    implicit val c1 = cbq1.message.get
    reply("Страсть к пироженым вполне обяснима, но у него нет собаки\n\nГде учился Мишутка?",replyMarkup = question4())
  }
  onCallbackWithTag(TAG32) { implicit cbq1 =>
    // Notification only shown to the user who pressed the button.
    ackCallback(cbq1.from.firstName + " pressed the button!")
    // Or just ackCallback()
    implicit val c1 = cbq1.message.get
    reply("Это не его собака, у него нет собаки.\n\nГде учился Мишутка?",replyMarkup = question4())
  }
  onCallbackWithTag(TAG33) { implicit cbq1 =>
    // Notification only shown to the user who pressed the button.
    ackCallback(cbq1.from.firstName + " pressed the button!")
    // Or just ackCallback()
    implicit val c1 = cbq1.message.get
    count+=1
    reply("Это правда, у него нет собаки\n\nГде учился Мишутка?",replyMarkup = question4())
  }
  onCallbackWithTag(TAG41) { implicit cbq1 =>
    // Notification only shown to the user who pressed the button.
    ackCallback(cbq1.from.firstName + " pressed the button!")
    // Or just ackCallback()
    implicit val c1 = cbq1.message.get
    count+=1
    reply("Это правда.\n\nСколько у Мишутки детей?",replyMarkup = question5())
  }
  onCallbackWithTag(TAG42) { implicit cbq1 =>
    // Notification only shown to the user who pressed the button.
    ackCallback(cbq1.from.firstName + " pressed the button!")
    // Or just ackCallback()
    implicit val c1 = cbq1.message.get
    reply("Страсть к кулинарии у Мишутки можно объяснить \nразными мотивами, но не его образованием.\n\nСколько у Мишутки детей?",replyMarkup = question5())
  }
  onCallbackWithTag(TAG51) { implicit cbq1 =>
    // Notification only shown to the user who pressed the button.
    ackCallback(cbq1.from.firstName + " pressed the button!")
    // Or just ackCallback()
    implicit val c1 = cbq1.message.get
    count+=1
    reply("Это правда.\n\nУ Мишутки нет детей")
    Thread.sleep(2000)
    count match {
      case 0 => reply("Вы с Мишуткой даже не знакомы, вам стоит это исправить.")
      case 1 => reply("Вы очень плохо знаете Мишутку, вам стоит поработать над этим.")
      case 2 => reply("Вы плохо знаете Мишутку, и очень зря.")
      case 3 => reply("Вы знаете Мишутку, но вам стоит узнать его получше.")
      case 4 => reply("Вы хорошо знаете Мишутку, но вам стоит узнать его получше.")
      case 5 => reply("Вы отлично знаете Мишутку, так держать.")
    }
   // if (count>5)  reply("Вы отлично знаете Мишутку, И усеете тыкать на кнопки))).")
    count=0
  }
  onCallbackWithTag(TAG52) { implicit cbq1 =>
    // Notification only shown to the user who pressed the button.
    ackCallback(cbq1.from.firstName + " pressed the button!")
    // Or just ackCallback()
    implicit val c1 = cbq1.message.get
    reply("Это не правда.\n\nУ Мишутки нет детей")
    Thread.sleep(2000)
    count match {
      case 0 => reply("Вы с Мишуткой даже не знакомы, вам стоит это исправить.")
      case 1 => reply("Вы очень плохо знаете Мишутку, вам стоит поработать над этим.")
      case 2 => reply("Вы плохо знаете Мишутку, и очень зря.")
      case 3 => reply("Вы знаете Мишутку, но вам стоит узнать его получше.")
      case 4 => reply("Вы хорошо знаете Мишутку, но вам стоит узнать его получше.")
      case 5 => reply("Вы отлично знаете Мишутку, так держать.")
    }
    //if (count>5)  reply("Вы отлично знаете Мишутку, И усеете тыкать на кнопки))).")
    count=0
  }

}
