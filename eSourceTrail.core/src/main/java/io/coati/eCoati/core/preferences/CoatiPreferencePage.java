package io.coati.eSourceTrail.core.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;

import io.coati.eSourceTrail.core.Activator;
import io.coati.eSourceTrail.core.preferences.PreferenceConstants;

import org.eclipse.ui.IWorkbench;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class CoatiPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public CoatiPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Configure the communication with coati:");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(
			new IntegerFieldEditor(
				PreferenceConstants.P_COATI_TO_ECLIPSE_PORT,
				"Port(Coati->Eclipse):",
				getFieldEditorParent()));
		addField(
			new IntegerFieldEditor(
				PreferenceConstants.P_ECLIPSE_TO_COATI_PORT,
				"Port(Eclipse->Coati):",
				getFieldEditorParent()));
		addField(
			new StringFieldEditor(
				PreferenceConstants.P_IP,
				"Ip:",
				getFieldEditorParent()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}
