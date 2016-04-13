'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('status_bill_service', {
                parent: 'app',
                url: '/status_bill_services',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_bill_services'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/status_bill_service/status_bill_services.html',
                        controller: 'Status_bill_serviceController'
                    }
                },
                resolve: {
                }
            })
            .state('status_bill_service.detail', {
                parent: 'app',
                url: '/status_bill_service/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_bill_service'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/status_bill_service/status_bill_service-detail.html',
                        controller: 'Status_bill_serviceDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Status_bill_service', function($stateParams, Status_bill_service) {
                        return Status_bill_service.get({id : $stateParams.id});
                    }]
                }
            })
            .state('status_bill_service.new', {
                parent: 'status_bill_service',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_bill_service/status_bill_service-dialog.html',
                        controller: 'Status_bill_serviceDialogController',
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
                        $state.go('status_bill_service', null, { reload: true });
                    }, function() {
                        $state.go('status_bill_service');
                    })
                }]
            })
            .state('status_bill_service.edit', {
                parent: 'status_bill_service',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_bill_service/status_bill_service-dialog.html',
                        controller: 'Status_bill_serviceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Status_bill_service', function(Status_bill_service) {
                                return Status_bill_service.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_bill_service', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('status_bill_service.delete', {
                parent: 'status_bill_service',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_bill_service/status_bill_service-delete-dialog.html',
                        controller: 'Status_bill_serviceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Status_bill_service', function(Status_bill_service) {
                                return Status_bill_service.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_bill_service', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
