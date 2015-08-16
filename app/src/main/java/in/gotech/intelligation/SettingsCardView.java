package in.gotech.intelligation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by anirudh on 16/08/15.
 */
public class SettingsCardView extends android.support.v7.widget.CardView {


    public SettingsCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.settings_card_children, this, true);
        setupChildren();
    }

    public static SettingsCardView inflate(ViewGroup parent) {
        SettingsCardView settingsCardView = (SettingsCardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_card, parent, false);
        return settingsCardView;
    }

    private void setupChildren() {

    }

}
