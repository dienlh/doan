'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('kind', {
                parent: 'entity',
                url: '/kinds',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Kinds'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/kind/kinds.html',
                        controller: 'KindController'
                    }
                },
                resolve: {
                }
            })
            .state('kind.detail', {
                parent: 'entity',
                url: '/kind/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Kind'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/kind/kind-detail.html',
                        controller: 'KindDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Kind', function($stateParams, Kind) {
                        return Kind.get({id : $stateParams.id});
                    }]
                }
            })
            .state('kind.new', {
                parent: 'kind',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/kind/kind-dialog.html',
                        controller: 'KindDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    decription: null,
                                    create_date: null,
                                    last_modified_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('kind', null, { reload: true });
                    }, function() {
                        $state.go('kind');
                    })
                }]
            })
            .state('kind.edit', {
                parent: 'kind',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/kind/kind-dialog.html',
                        controller: 'KindDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Kind', function(Kind) {
                                return Kind.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('kind', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('kind.delete', {
                parent: 'kind',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/kind/kind-delete-dialog.html',
                        controller: 'KindDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Kind', function(Kind) {
                                return Kind.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('kind', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
