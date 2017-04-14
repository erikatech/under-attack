(function () {
	'use strict';

	/**
	* @ngdoc function
	* @name app.controller:DashboardCtrl
	* @description
	* # DashboardCtrl
	* Controller of the app
	*/

	angular
		.module('supplychain-web')
		.controller('DashboardCtrl', Dashboard);

	Dashboard.$inject = ['homeService', '$mdSidenav', 'AuthService'];

	/*
	* recommend
	* Using function declarations
	* and bindable members up top.
	*/

	function Dashboard(homeService, $mdSidenav, AuthService) {
		var vm = this;
	}

})();
