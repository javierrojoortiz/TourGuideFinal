package tourguide.visual;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;

public class RestaurantesForm extends FormLayout{

	private Button addNewBtn = new Button();
	
	private HorizontalLayout horizontalLayout = new HorizontalLayout();
	
	private VaadinUI vaadinUI;
	
	public RestaurantesForm(VaadinUI vaadinUI) {
		this.vaadinUI = vaadinUI;
		
		horizontalLayout.addComponents(addNewBtn);
		addComponents(horizontalLayout);
	}
}
