package base.comm.bind;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;

import base.biz.sample.SampleController;

public class BaseBindingInitializer implements WebBindingInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseBindingInitializer.class);
	
	@Override
	public void initBinder(WebDataBinder binder) {
		LOGGER.debug("???????????? BaseBindingInitializer initBinder ?????????????");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}

}
