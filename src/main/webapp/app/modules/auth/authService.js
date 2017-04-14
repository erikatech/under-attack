(function() {
	'use strict';

	/**
	 * @ngdoc function
	 * @name app.service:authService
	 * @description
	 * # authService
	 * Service of the app
	 */

  	angular
		.module('auth')
		.factory('AuthService', Auth);

		Auth.$inject = [];

		function Auth () {

		}
})();
