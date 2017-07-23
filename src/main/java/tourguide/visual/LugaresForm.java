package tourguide.visual;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;



public class LugaresForm extends FormLayout  {



	private Button addNewBtn=new Button("Botón de prueba de carga de LugaresForm");
	
	
	
	private HorizontalLayout horizontalLayout = new HorizontalLayout();
	
	private VaadinUI vaadinUI;
	
	public LugaresForm(VaadinUI vaadinUI) {
		this.vaadinUI = vaadinUI;

		horizontalLayout.addComponents(addNewBtn);
		addComponents(horizontalLayout);
		
				
	}	


}


