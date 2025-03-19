// angular.module('mySite').factory('ProductService', ['$http', function($http) {
//     var baseUrl = '/api/products/suggestions';

//     return {
//         getSuggestions: function(keywords) {
//             return $http.get(baseUrl, { params: { keywords: keywords } });
//         }
//     };
// }]);

// angular.module('mySite').controller('siteCtrl', ['$scope', 'ProductService', function($scope, ProductService) {
//     $scope.searchQuery = '';
//     $scope.searchResults = [];

//     $scope.search = function() {
//         if ($scope.searchQuery.length > 2) { 
//             ProductService.getSuggestions($scope.searchQuery).then(function(response) {
//                 $scope.searchResults = response.data;
//             }, function(error) {
//                 console.error('Error fetching suggestions:', error);
//             });
//         } else {
//             $scope.searchResults = [];
//         }
//     };


// }]);
