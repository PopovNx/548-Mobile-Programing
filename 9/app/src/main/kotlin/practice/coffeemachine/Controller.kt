package practice.coffeemachine

class Controller(private val model: CoffeeMachine) {
    private lateinit var view: View

    fun attachView(view: View) {
        this.view = view
    }

    fun start() = view.start()

    fun enterBuy() = view.display(model.enterBuy())
    fun enterFill() = view.display(model.enterFill())
    fun back() = view.display(model.back())

    fun buy(order: Order) = view.display(model.buy(order))
    fun fill(resources: Resources) = view.display(model.fill(resources))
    fun take() = view.display(model.take())
    fun status() = view.display(model.status())
}
