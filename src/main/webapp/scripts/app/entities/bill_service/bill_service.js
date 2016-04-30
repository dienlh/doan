'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bill_service', {
                parent: 'app',
                url: '/bill_services',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Bill_services'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/bill_service/bill_services.html',
                        controller: 'Bill_serviceController'
                    }
                },
                resolve: {
                }
            })
            .state('bill_service.detail', {
                parent: 'app',
                url: '/bill_service/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Bill_service'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/bill_service/bill_service-detail.html',
                        controller: 'Bill_serviceDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Bill_service', function($stateParams, Bill_service) {
                        return Bill_service.get({id : $stateParams.id});
                    }]
                }
            })
            .state('bill_service.new', {
                parent: 'bill_service',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bill_service/bill_service-dialog.html',
                        controller: 'Bill_serviceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    quantity: null,
                                    total: null,
                                    decription: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('bill_service', null, { reload: true });
                    }, function() {
                        $state.go('bill_service');
                    })
                }]
            })
            .state('bill_service.edit', {
                parent: 'bill_service',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bill_service/bill_service-dialog-update.html',
                        controller: 'Bill_serviceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Bill_service', function(Bill_service) {
                                return Bill_service.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bill_service', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('bill_service.delete', {
                parent: 'bill_service',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bill_service/bill_service-delete-dialog.html',
                        controller: 'Bill_serviceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Bill_service', function(Bill_service) {
                                return Bill_service.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bill_service', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('bill_service.printer', {
                parent: 'bill_service',
                url: '/printer?fromDate&toDate&serviceId&reservationId&statusId',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bill_service/bill_service-printer.html',
                        controller: 'Bill_servicePrinterController',
                        size: 'lg',
                        resolve: {
//                            entity: ['Bill_service', function(Bill_service) {
//                                return Bill_service.get({id : $stateParams.id});
//                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bill_service', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            ;
    });
