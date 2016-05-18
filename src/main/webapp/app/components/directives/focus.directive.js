(function() {
    'use strict';
    angular
        .module('kioskApp')
        .directive("ngFocus", function () {

            var directive = {
                restrict: 'A',
                link: function (scope, elem, attrs) {
                    elem.bind('keydown', function (e) {
                        var code = e.keyCode || e.which;
                        if (code === 13) {
                            try {
                                if (attrs.tabindex != undefined) {
                                    var currentTabIndex = attrs.tabindex;
                                    var nextTabIndex = parseInt(attrs.tabindex) + 1;
                                    $("[tabindex=" + nextTabIndex + "]").focus();
                                }
                            } catch (e) {

                            }
                        }
                    });
                }
            };
            return directive;
        });
})();
