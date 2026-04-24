package practice.coffeemachine

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Font
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.JTextField
import javax.swing.SwingUtilities
import javax.swing.UIManager

class SwingView(private val controller: Controller) : View {

    private val coffeeBrown = Color(0x6F4E37)
    private val cream = Color(0xF5EFE6)
    private val accent = Color(0xB8860B)

    private val frame = JFrame("Coffee Machine")
    private val messageLabel = JLabel("Ready").apply {
        font = Font("SansSerif", Font.BOLD, 15)
        foreground = coffeeBrown
    }
    private val statusArea = JTextArea(5, 30).apply {
        isEditable = false
        isOpaque = false
        font = Font("Monospaced", Font.PLAIN, 12)
        foreground = coffeeBrown
    }
    private val fields = mapOf(
        "Water" to JTextField("0", 4),
        "Milk" to JTextField("0", 4),
        "Beans" to JTextField("0", 4),
        "Cups" to JTextField("0", 4),
    )

    private fun styleButton(btn: JButton, bg: Color = coffeeBrown): JButton = btn.apply {
        font = Font("SansSerif", Font.BOLD, 13)
        background = bg
        foreground = Color.WHITE
        isFocusPainted = false
        border = BorderFactory.createEmptyBorder(8, 16, 8, 16)
    }

    private fun panel(title: String, build: JPanel.() -> Unit): JPanel =
        JPanel(FlowLayout(FlowLayout.CENTER, 8, 8)).apply {
            background = cream
            border = BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(coffeeBrown, 1, true),
                    title,
                ).apply { titleFont = Font("SansSerif", Font.BOLD, 13) },
                BorderFactory.createEmptyBorder(4, 8, 4, 8),
            )
            build()
        }

    private fun buildUI() {
        runCatching { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()) }

        val infoPanel = JPanel(BorderLayout(6, 6)).apply {
            background = cream
            border = BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(coffeeBrown, 1, true),
                    "Info",
                ).apply { titleFont = Font("SansSerif", Font.BOLD, 13) },
                BorderFactory.createEmptyBorder(6, 10, 6, 10),
            )
            add(messageLabel, BorderLayout.NORTH)
            add(statusArea, BorderLayout.CENTER)
        }

        val actions = JPanel(GridLayout(3, 1, 6, 6)).apply {
            background = cream
            add(panel("Order coffee") {
                listOf(Coffee.ESPRESSO, Coffee.LATTE, Coffee.CAPPUCCINO).forEach { c ->
                    add(styleButton(JButton(c.name.lowercase().replaceFirstChar { it.uppercase() })).apply {
                        addActionListener { controller.buy(Order(c)) }
                    })
                }
            })
            add(panel("Fill resources") {
                fields.forEach { (label, field) ->
                    add(JLabel("$label:").apply { foreground = coffeeBrown })
                    add(field)
                }
                add(styleButton(JButton("Fill"), accent).apply {
                    addActionListener {
                        controller.fill(
                            Resources(
                                water = fields.getValue("Water").text.toIntOrNull() ?: 0,
                                milk = fields.getValue("Milk").text.toIntOrNull() ?: 0,
                                beans = fields.getValue("Beans").text.toIntOrNull() ?: 0,
                                cups = fields.getValue("Cups").text.toIntOrNull() ?: 0,
                            )
                        )
                        fields.values.forEach { it.text = "0" }
                    }
                })
            })
            add(panel("Cash") {
                add(styleButton(JButton("Take money"), accent).apply {
                    addActionListener { controller.take() }
                })
            })
        }

        val content = JPanel(BorderLayout(6, 6)).apply {
            background = cream
            border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
            preferredSize = Dimension(680, 500)
            add(infoPanel, BorderLayout.NORTH)
            add(actions, BorderLayout.CENTER)
        }

        frame.contentPane = content
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.pack()
        frame.setLocationRelativeTo(null)
    }

    override fun start() {
        SwingUtilities.invokeLater {
            buildUI()
            controller.status()
            frame.isVisible = true
        }
    }

    override fun display(response: Response) {
        messageLabel.text = response.message
        statusArea.text = response.status
    }
}
