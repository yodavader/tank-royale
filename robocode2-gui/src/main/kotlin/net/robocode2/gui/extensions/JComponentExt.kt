package net.robocode2.gui.extensions

import io.reactivex.subjects.PublishSubject
import net.robocode2.gui.ResourceBundles
import javax.swing.*

object JComponentExt {

    fun JComponent.addNewLabel(stringResourceName: String) : JLabel {
        val label = JLabel(ResourceBundles.STRINGS.get(stringResourceName))
        add(label)
        return label
    }

    fun JComponent.addNewButton(stringResourceName: String, publishSubject: PublishSubject<Unit>,
                                layoutConstraints: String? = null)
            : JButton {
        val button = JButton(ResourceBundles.STRINGS.get(stringResourceName))
        button.addActionListener { publishSubject.onNext(Unit) }
        parent.add(button, layoutConstraints)
        return button
    }
}
