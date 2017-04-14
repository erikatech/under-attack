(function () {
	'use strict';

	/**
	* @ngdoc function
	* @name app.controller:HomeCtrl
	* @description
	* # HomeCtrl
	* Controller of the app
	*/

	angular
		.module('supplychain-web')
		.controller('HomeCtrl', Home);

	Home.$inject = ['$scope', '$mdSidenav', 'CustomToastService', '$rootScope'];

	/*
	* recommend
	* Using function declarations
	* and bindable members up top.
	*/

	function Home($scope, $mdSidenav, CustomToastService, $rootScope) {
		/*jshint validthis: true */
		var vm = this;
		vm.title = "Hello, supplychain-web!";
		vm.version = "1.0.0";

		vm.toggleSideNav = toggleSideNav;

		if(!localStorage.getItem('isLockedMenu')) {
			localStorage.setItem('isLockedMenu', 'true');
		}
		else {
			$rootScope.isLockedMenu = (localStorage.getItem('isLockedMenu') === 'true');
		}

		$rootScope.isLockedMenu = true;

		$scope.$watch('online', function(isOnline) {
			if(!isOnline)
				CustomToastService.show("Você está offline. Algumas funcionalidades podem não funcionar normalmente",
					"top right", 5000);
		});

		function toggleSideNav(){
			$mdSidenav('left').toggle();
		}

	}

})();
