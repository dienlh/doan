'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('marital_status', {
                parent: 'entity',
                url: '/marital_statuss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Marital_statuss'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/marital_status/marital_statuss.html',
                        controller: 'Marital_statusController'
                    }
                },
                resolve: {
                }
            })
            .state('marital_status.detail', {
                parent: 'entity',
                url: '/marital_status/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Marital_status'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/marital_status/marital_status-detail.html',
                        controller: 'Marital_statusDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Marital_status', function($stateParams, Marital_status) {
                        return Marital_status.get({id : $stateParams.id});
                    }]
                }
            })
            .state('marital_status.new', {
                parent: 'marital_status',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/marital_status/marital_status-dialog.html',
                        controller: 'Marital_statusDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('marital_status', null, { reload: true });
                    }, function() {
                        $state.go('marital_status');
                    })
                }]
            })
            .state('marital_status.edit', {
                parent: 'marital_status',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/marital_status/marital_status-dialog.html',
                        controller: 'Marital_statusDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Marital_status', function(Marital_status) {
                                return Marital_status.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('marital_status', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('marital_status.delete', {
                parent: 'marital_status',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/marital_status/marital_status-delete-dialog.html',
                        controller: 'Marital_statusDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Marital_status', function(Marital_status) {
                                return Marital_status.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('marital_status', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
