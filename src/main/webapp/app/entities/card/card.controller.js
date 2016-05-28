(function() {
    'use strict';

    angular
        .module('kioskApp')
        .controller('CardController', CardController);

    CardController.$inject = ['$scope', '$state', 'Card', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function CardController ($scope, $state, Card, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;
        vm.number= "";
        vm.ownerName = "";
        vm.smsNumber = "";
        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.loadAll();

        function loadAll () {
            Card.query({
                number: vm.number,
                ownerName: vm.ownerName,
                smsNumber: vm.smsNumber,
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.cards = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

    }
})();
