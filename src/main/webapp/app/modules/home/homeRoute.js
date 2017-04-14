'use strict';

	/**
	* @ngdoc function
	* @name app.route:HomeRoute
	* @description
	* # HomeRoute
	* Route of the app
	*/

angular.module('home')
	.config(['$stateProvider', function ($stateProvider) {
		$stateProvider
			.state('authenticated', {
				cache: false,
				abstract: true,
				templateUrl: 'app/modules/home/authenticated.html',
				resolve: {
					user: ['$rootScope', '$q', 'AuthService', '$state', '$location', function ($rootScope, $q, AuthService, $state, $location) {
						return  AuthService.getUser()
								.then(function (response) {
									return $q.resolve(response);
								})
								.catch(function (err) {
									console.error('Something went wrong when trying to get the user data');
									$state.go('forbidden');
								});
					}]
				}
			})
			.state('authenticated.home', {
				cache: false,
				abstract: true,
				templateUrl: 'app/modules/home/home.html',
				controller: 'HomeCtrl',
				controllerAs: 'vm'
			})
			.state('authenticated.home.dashboard', {
				cache: false,
				url:'/dashboard',
				templateUrl: 'app/modules/home/dashboard.html',
				controller: 'DashboardCtrl',
				controllerAs: 'vm'
			});

	}]);
