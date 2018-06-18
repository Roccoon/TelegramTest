import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api.declarative.{Callbacks, Commands}
import info.mukel.telegrambot4s.api.{Extractors, Polling, TelegramBot}
import info.mukel.telegrambot4s.methods.{EditMessageReplyMarkup, SendPhoto}
import info.mukel.telegrambot4s.models.{ChatId, InlineKeyboardButton, InlineKeyboardMarkup}

/**
  *
  * https://www.google.ru/imgres?imgurl=https%3A%2F%2Fwww.vl.ru%2Fafisha%2Fuploads%2Fevents%2Fbed%2F61918_big.jpeg%3Fv%3D1492769770&imgrefurl=https%3A%2F%2Fwww.vl.ru%2Fafisha%2Fvladivostok%2Fevent%2F61918&docid=4Eo08oeAjj7cBM&tbnid=58-RevT3xadG5M%3A&vet=10ahUKEwiPlYqF3tvbAhVEfbwKHXtSAPsQMwhBKAcwBw..i&w=663&h=450&bih=864&biw=1328&q=%D0%BC%D0%B8%D1%85%D0%B0%D0%B8%D0%BB%20%D0%B5%D1%88%D1%82%D0%BE%D0%BA%D0%B8%D0%BD&ved=0ahUKEwiPlYqF3tvbAhVEfbwKHXtSAPsQMwhBKAcwBw&iact=mrc&uact=8
  * Show how to use callbacks, and it's shortcomings.
  *
  * @param token Bot's token.
  */
class ThatYouKnowBot(val token: String) extends TelegramBot
  with Polling
  with Commands
  with Callbacks {

  val TAG1 = "COUNTER_TAG1"
  val TAG2 = "COUNTER_TAG2"
  val TAG3 = "COUNTER_TAG3"
  var requestCount = 0
def question1():InlineKeyboardMarkup={
  val button1=InlineKeyboardButton.callbackData("300",TAG1)
  val button2=InlineKeyboardButton.callbackData("200",TAG2)
  val twoButton=Seq(button1, button2)
  InlineKeyboardMarkup.singleRow(twoButton)
}
  def question2():InlineKeyboardMarkup={
    val button1=InlineKeyboardButton.callbackData("300",TAG1)
    val button2=InlineKeyboardButton.callbackData("400",TAG3)
    val twoButton=Seq(button1, button2)
    InlineKeyboardMarkup.singleRow(twoButton)
  }
  //def tag = prefixTag(TAG) _
  onCommand("/start") { implicit msg =>
    println("s")
    reply("Press to 300", replyMarkup = question1())

  }

  onCallbackWithTag(TAG1) { implicit cbq1 =>
    // Notification only shown to the user who pressed the button.
    ackCallback(cbq1.from.firstName + " pressed the button!")
    // Or just ackCallback()
     implicit val c1 = cbq1.message.get
    reply("Отс**си у Тракториста")
  }

    onCallbackWithTag(TAG2) { implicit cbq =>
      // Notification only shown to the user who pressed the button.
      ackCallback(cbq.from.firstName + " pressed the button!")
      // Or just ackCallback()
      implicit val c2= cbq.message.get
      reply("Попытка номер 2",replyMarkup = question2())
  }
  onCallbackWithTag(TAG3) { implicit cbq1 =>
    // Notification only shown to the user who pressed the button.
    ackCallback(cbq1.from.firstName + " pressed the button!")
    // Or just ackCallback()
    implicit val c1 = cbq1.message.get
    reply("а ты хорош!")
   // SendPhoto()
  }
}
