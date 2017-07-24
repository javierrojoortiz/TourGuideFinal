package tourguide.logica;

import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.argThat;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tourguide.logica.Hotel;
import tourguide.logica.HotelEditor;
import tourguide.logica.HotelRepository;

@RunWith(MockitoJUnitRunner.class)
public class CustomerEditorTests {

	private static final String FIRST_NAME = "Marcin";
	private static final String LAST_NAME = "Grzejszczak";

	@Mock HotelRepository customerRepository;
	@InjectMocks HotelEditor editor;

	@Test
	public void shouldStoreCustomerInRepoWhenEditorSaveClicked() {
		this.editor.nombre.setValue(FIRST_NAME);
		this.editor.direccion.setValue(LAST_NAME);
		customerDataWasFilled();

		this.editor.save.click();

		then(this.customerRepository).should().save(argThat(customerMatchesEditorFields()));
	}

	@Test
	public void shouldDeleteCustomerFromRepoWhenEditorDeleteClicked() {
		this.editor.nombre.setValue(FIRST_NAME);
		this.editor.direccion.setValue(LAST_NAME);
		customerDataWasFilled();

		editor.delete.click();

		then(this.customerRepository).should().delete(argThat(customerMatchesEditorFields()));
	}

	private void customerDataWasFilled() {
		this.editor.editCustomer(new Hotel());
	}

	private TypeSafeMatcher<Hotel> customerMatchesEditorFields() {
		return new TypeSafeMatcher<Hotel>() {
			@Override
			public void describeTo(Description description) {}

			@Override
			protected boolean matchesSafely(Hotel item) {
				return FIRST_NAME.equals(item.getNombre()) && LAST_NAME.equals(item.getDireccion());
			}
		};
	}

}
