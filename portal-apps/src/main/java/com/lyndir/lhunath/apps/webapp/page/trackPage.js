var _gaq = _gaq || [];
_gaq.push(['lhunath._setAccount',		'UA-90535-8']);
_gaq.push(['lhunath._setDomainName',	'.lhunath.com']);
_gaq.push(['lhunath._trackPageview',	'/${pageView}']);
_gaq.push(['app._setAccount',			'${googleAnalyticsID}']);
_gaq.push(['app._trackPageview',		'/${pageView}']);

(function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(ga);
})();