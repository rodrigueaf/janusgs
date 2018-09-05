angular.module('app')
    .directive('uiSelectWrap', uiSelectWrap);

uiSelectWrap.$inject = ['$document', 'uiGridEditConstants'];

function uiSelectWrap($document, uiGridEditConstants) {
    return function link($scope, $elm, $attr) {
        $document.on('click', docClick);

        function docClick(evt) {
            var obj = $(evt.target).closest('.ui-select-container');
            if (obj.prevObject.length !== 0 && obj.prevObject[0].nodeName === 'SPAN' && obj.length === 1) {
                $scope.$emit(uiGridEditConstants.events.END_CELL_EDIT);
                $document.off('click', docClick);
            }
        }
    };
}