package practice.coffeemachine

fun main(args: Array<String>) {
    val model = CoffeeMachine()
    val controller = Controller(model)
    val view = if (args.firstOrNull() == "swing") SwingView(controller) else TerminalView(controller)
    controller.attachView(view)
    controller.start()
}
