(function () {
	'use strict';

	/**
	 * @ngdoc configuration file
	 * @name app.config:config
	 * @description
	 * # Config and run block
	 * Configutation of the app
	 */
	angular
		.module('under-attack')
		.config(configure)
		.config(configureLoadingBar);
		// .run(runBlock);

	configure.$inject = ['$urlRouterProvider', '$locationProvider', '$httpProvider'];
	function configure($urlRouterProvider, $locationProvider, $httpProvider) {
		$locationProvider.hashPrefix('!');
		$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
		$urlRouterProvider.otherwise('/');
	}

	configureLoadingBar.$inject = ['cfpLoadingBarProvider'];
	function configureLoadingBar(cfpLoadingBarProvider){
		cfpLoadingBarProvider.includeSpinner = false;
	}
	/*runBlock.$inject = ['$rootScope'];
	function runBlock($rootScope) {
		'use strict';
		console.log('AngularJS run() function...');
	}*/
})();
