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
		.module('admin')
		.controller('AdminCtrl', Admin);

	Admin.$inject = [''];

	/*
	* recommend
	* Using function declarations
	* and bindable members up top.
	*/

	function Admin() {
		/*jshint validthis: true */
		var vm = this;
		vm.title = "Hello, under-attack!";
		vm.version = "1.0.0";

	}

})();
