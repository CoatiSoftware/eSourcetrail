package io.coati.eSourceTrail.core.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import io.coati.eSourceTrail.core.Activator;
import io.coati.eSourceTrail.core.preferences.PreferenceConstants;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_BOOLEAN, true);
		store.setDefault(PreferenceConstants.P_CHOICE, "choice2");
		store.setDefault(PreferenceConstants.P_STRING,
				"Default value");
		store.setDefault(PreferenceConstants.P_COATI_TO_ECLIPSE_PORT, 6666);
		store.setDefault(PreferenceConstants.P_ECLIPSE_TO_COATI_PORT, 6667);
		store.setDefault(PreferenceConstants.P_IP, "localhost");
	}

}
