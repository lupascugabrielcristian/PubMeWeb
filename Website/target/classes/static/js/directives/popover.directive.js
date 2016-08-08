angular.module('PubMeApp').directive('popover', function () {
    return {
        restrict: 'A',
        template: '',
        link: function (scope, el, attrs) {
            scope.label = attrs.popoverLabel;
            $(el).popover({
                trigger: 'hover',
                html: true,
                content: attrs.popoverHtml,
                placement: "bottom"
            });
        }
    };
});
