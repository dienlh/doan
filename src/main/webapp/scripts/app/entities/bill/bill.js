'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bill', {
                parent: 'entity',
                url: '/bills',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Bills'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bill/bills.html',
                        controller: 'BillController'
                    }
                },
                resolve: {
                }
            })
            .state('bill.detail', {
                parent: 'entity',
                url: '/bill/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Bill'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bill/bill-detail.html',
                        controller: 'BillDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Bill', function($stateParams, Bill) {
                        return Bill.get({id : $stateParams.id});
                    }]
                }
            })
            .state('bill.new', {
                parent: 'bill',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bill/bill-dialog.html',
                        controller: 'BillDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    fees_room: null,
                                    fees_service: null,
                                    fees_other: null,
                                    fees_bonus: null,
                                    total: null,
                                    fees_vat: null,
                                    total_vat: null,
                                    decription: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('bill', null, { reload: true });
                    }, function() {
                        $state.go('bill');
                    })
                }]
            })
            .state('bill.edit', {
                parent: 'bill',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bill/bill-dialog.html',
                        controller: 'BillDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Bill', function(Bill) {
                                return Bill.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bill', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('bill.delete', {
                parent: 'bill',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bill/bill-delete-dialog.html',
                        controller: 'BillDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Bill', function(Bill) {
                                return Bill.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bill', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
