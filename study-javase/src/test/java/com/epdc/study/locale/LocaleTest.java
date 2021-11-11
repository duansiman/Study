package com.epdc.study.locale;

import org.junit.Test;
import sun.util.locale.provider.LocaleDataMetaInfo;
import sun.util.locale.provider.LocaleNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.SPILocaleProviderAdapter;

import java.util.Locale;
import java.util.ServiceLoader;
import java.util.spi.LocaleServiceProvider;

/**
 * Created by devin on 2021-11-09.
 */
public class LocaleTest {

	@Test
	public void list() {
		Locale[] locales = Locale.getAvailableLocales();
		System.out.println(locales.length);
		for (int i = 0; i < locales.length; i++) {
			System.out.println(locales[i]);
		}
	}

	@Test
	public void test() {
//		Locale locale = new Locale("in", "ID");
//		ServiceLoader.loadInstalled(LocaleDataMetaInfo.class).forEach(localeDataMetaInfo -> {
//			System.out.println(localeDataMetaInfo);
//		});

//		for (LocaleProviderAdapter.Type type : LocaleProviderAdapter.getAdapterPreference()) {
//			LocaleProviderAdapter lda = LocaleProviderAdapter.forType(type);
//			if (lda != null) {
//				LocaleServiceProvider lsp = lda.getLocaleServiceProvider(java.util.spi.LocaleNameProvider.class);
//				if (lsp != null) {
//					Locale[] locales = lsp.getAvailableLocales();
//					for (Locale locale: locales) {
//						System.out.println(locale);
//					}
//				}
//			}
//		}

		System.out.println(SPILocaleProviderAdapter.class.getCanonicalName() +
				"$" +
				java.util.spi.LocaleNameProvider.class.getSimpleName() +
				"Delegate");
	}

}
