/jre/lib/ext/localedata.jar

#### LocaleServiceProvider
This is the super class of all the locale sensitive service provider interfaces (SPIs).

A provider identifies itself with a provider-configuration file in the resource directory META-INF/services, 
using the fully qualified provider interface class name as the file name.

可以使用“java.locale.providers”系统属性配置locale敏感服务的搜索顺序
> java.locale.providers=SPI,JRE

#### LocaleServiceProviderPool
An instance of this class holds a set of the third party implementations of a particular locale sensitive service

sun.util.locale.provider.LocaleServiceProviderPool.AllAvailableLocales#allAvailableLocales

##### 所有的SPI类
java.text.spi.BreakIteratorProvider.class,
java.text.spi.CollatorProvider.class,
java.text.spi.DateFormatProvider.class,
java.text.spi.DateFormatSymbolsProvider.class,
java.text.spi.DecimalFormatSymbolsProvider.class,
java.text.spi.NumberFormatProvider.class,
java.util.spi.CurrencyNameProvider.class,
java.util.spi.LocaleNameProvider.class,
java.util.spi.TimeZoneNameProvider.class,
java.util.spi.CalendarDataProvider.class

#### ResourceBundleBasedAdapter
Accessor for LocaleData，实现类JRELocaleProviderAdapter， 父类LocaleProviderAdapter

sun.util.locale.provider.JRELocaleProviderAdapter#getLocaleServiceProvider

#### JRELocaleProviderAdapter
##### LocaleName
sun.util.locale.provider.LocaleNameProviderImpl
category: LocaleNames

##### 查找sun.util.locale.provider.LocaleDataMetaInfo的SPI
LocaleDataMetaInfo ldmi : ServiceLoader.loadInstalled(LocaleDataMetaInfo.class)
sun.util.locale.provider.LocaleDataMetaInfo#availableLanguageTags

#### SPILocaleProviderAdapter
Delegate代理 LocaleServiceProvider provider : ServiceLoader.load(c, ClassLoader.getSystemClassLoader())
sun.util.locale.provider.SPILocaleProviderAdapter$LocaleNameProviderDelegate