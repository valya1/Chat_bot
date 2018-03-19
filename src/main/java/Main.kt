import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi

fun main(args: Array<String>){

    ApiContextInitializer.init()
    TelegramBotsApi().apply { registerBot(MyFirstBot()) }
}
