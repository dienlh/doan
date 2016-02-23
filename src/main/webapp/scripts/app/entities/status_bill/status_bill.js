'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('status_bill', {
                parent: 'entity',
                url: '/status_bills',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_bills'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/status_bill/status_bills.html',
                        controller: 'Status_billController'
                    }
                },
                resolve: {
                }
            })
            .state('status_bill.detail', {
                parent: 'entity',
                url: '/status_bill/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_bill'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/status_bill/status_bill-detail.html',
                        controller: 'Status_billDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Status_bill', function($stateParams, Status_bill) {
                        return Status_bill.get({id : $stateParams.id});
                    }]
                }
            })
            .state('status_bill.new', {
                parent: 'status_bill',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_bill/status_bill-dialog.html',
                        controller: 'Status_billDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    decription: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('status_bill', null, { reload: true });
                    }, function() {
                        $state.go('status_bill');
                    })
                }]
            })
            .state('status_bill.edit', {
                parent: 'status_bill',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_bill/status_bill-dialog.html',
                        controller: 'Status_billDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Status_bill', function(Status_bill) {
                                return Status_bill.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_bill', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('status_bill.delete', {
                parent: 'status_bill',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_bill/status_bill-delete-dialog.html',
                        controller: 'Status_billDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Status_bill', function(Status_bill) {
                                return Status_bill.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_bill', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
