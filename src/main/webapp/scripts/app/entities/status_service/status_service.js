'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('status_service', {
                parent: 'app',
                url: '/status_services',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_services'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/status_service/status_services.html',
                        controller: 'Status_serviceController'
                    }
                },
                resolve: {
                }
            })
            .state('status_service.detail', {
                parent: 'app',
                url: '/status_service/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_service'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/status_service/status_service-detail.html',
                        controller: 'Status_serviceDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Status_service', function($stateParams, Status_service) {
                        return Status_service.get({id : $stateParams.id});
                    }]
                }
            })
            .state('status_service.new', {
                parent: 'status_service',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_service/status_service-dialog.html',
                        controller: 'Status_serviceDialogController',
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
                        $state.go('status_service', null, { reload: true });
                    }, function() {
                        $state.go('status_service');
                    })
                }]
            })
            .state('status_service.edit', {
                parent: 'status_service',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_service/status_service-dialog.html',
                        controller: 'Status_serviceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Status_service', function(Status_service) {
                                return Status_service.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_service', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('status_service.delete', {
                parent: 'status_service',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_service/status_service-delete-dialog.html',
                        controller: 'Status_serviceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Status_service', function(Status_service) {
                                return Status_service.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_service', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
