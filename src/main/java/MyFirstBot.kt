import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import java.util.*
import java.util.concurrent.TimeUnit

class MyFirstBot : TelegramLongPollingBot() {

    companion object{
        const val BOT_TOKEN = "539023876:AAFGAyJ8NWopRFxnY2AEAYBnIV_NjNy6uCs"
        const val BOT_USERNAME = "med_univ_test_bot"

        val backStack = Stack<String>()
    }

    lateinit var message : SendMessage

    override fun getBotToken(): String = BOT_TOKEN

    override fun getBotUsername(): String = BOT_USERNAME

    override fun onUpdateReceived(update: Update?) {

        if(update?.hasMessage() == true && update.message.hasText()){
            println(update.message.text)
            message = SendMessage()
                    .setChatId(update.message.chatId)
                    .setText("Here is your keyboard deployment")
                    .setReplyMarkup(createKeyboard())
            execute(message)
            TimeUnit.SECONDS.sleep(5)
        }
    }

    fun createKeyboard() : ReplyKeyboardMarkup {
        return  ReplyKeyboardMarkup().apply {
            keyboard = listOf(
                    KeyboardRow().apply { add(KeyboardButton("Студенту"))},
                    KeyboardRow().apply { add(KeyboardButton("Об университете")) },
                    KeyboardRow().apply { add(KeyboardButton("Новостной раздел")) },
                    KeyboardRow().apply { addAll( arrayOf( KeyboardButton("Расписание"), KeyboardButton("Еще какая-нибудь дичь") )) }
            )
        }
    }
}