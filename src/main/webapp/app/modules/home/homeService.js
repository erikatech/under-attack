(function () {
	'use strict';

	/**
	* @ngdoc function
	* @name app.service:homeService
	* @description
	* # homeService
	* Service of the app
	*/

	angular.module('supplychain-web')
		.factory('homeService', homeService);

	homeService.$inject = ['$http'];

	function homeService($http) {
		return {};
	}

})();
