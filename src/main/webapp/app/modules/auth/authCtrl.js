(function() {
	'use strict';

	/**
	* @ngdoc function
	* @name app.controller:authCtrl
	* @description
	* # authCtrl
	* Controller of the app
	*/

  	angular
		.module('auth')
		.controller('AuthCtrl', AuthCtrl);

		AuthCtrl.$inject = ['$state', '$interval'];

		function AuthCtrl($state, $interval) {
			var context = this;

		}
})();
