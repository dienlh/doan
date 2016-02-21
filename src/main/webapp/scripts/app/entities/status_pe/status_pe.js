'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('status_pe', {
                parent: 'entity',
                url: '/status_pes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_pes'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/status_pe/status_pes.html',
                        controller: 'Status_peController'
                    }
                },
                resolve: {
                }
            })
            .state('status_pe.detail', {
                parent: 'entity',
                url: '/status_pe/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_pe'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/status_pe/status_pe-detail.html',
                        controller: 'Status_peDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Status_pe', function($stateParams, Status_pe) {
                        return Status_pe.get({id : $stateParams.id});
                    }]
                }
            })
            .state('status_pe.new', {
                parent: 'status_pe',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_pe/status_pe-dialog.html',
                        controller: 'Status_peDialogController',
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
                        $state.go('status_pe', null, { reload: true });
                    }, function() {
                        $state.go('status_pe');
                    })
                }]
            })
            .state('status_pe.edit', {
                parent: 'status_pe',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_pe/status_pe-dialog.html',
                        controller: 'Status_peDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Status_pe', function(Status_pe) {
                                return Status_pe.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_pe', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('status_pe.delete', {
                parent: 'status_pe',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_pe/status_pe-delete-dialog.html',
                        controller: 'Status_peDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Status_pe', function(Status_pe) {
                                return Status_pe.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_pe', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
