package med.voll.api.domain.medico;

import org.antlr.v4.runtime.misc.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosRegistroMedico(
		
		
		@NotBlank
		String nombre,
		
		@NotBlank
		@Email
		String email,
		
		@NotBlank
		String telefono,
		
		@NotBlank
		@Pattern(regexp = "\\d{4,6}")
		String documento,
		
		@jakarta.validation.constraints.NotNull
		Especialidad especialidad,
		
		@jakarta.validation.constraints.NotNull
		@Valid
		med.voll.api.domain.direccion.DatosDireccion direccion) {

	  
		
		
	}




