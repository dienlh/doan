'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('position', {
                parent: 'app',
                url: '/positions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Positions'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/position/positions.html',
                        controller: 'PositionController'
                    }
                },
                resolve: {
                }
            })
            .state('position.detail', {
                parent: 'app',
                url: '/position/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Position'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/position/position-detail.html',
                        controller: 'PositionDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Position', function($stateParams, Position) {
                        return Position.get({id : $stateParams.id});
                    }]
                }
            })
            .state('position.new', {
                parent: 'position',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/position/position-dialog.html',
                        controller: 'PositionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    position: null,
                                    decription: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('position', null, { reload: true });
                    }, function() {
                        $state.go('position');
                    })
                }]
            })
            .state('position.edit', {
                parent: 'position',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/position/position-dialog.html',
                        controller: 'PositionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Position', function(Position) {
                                return Position.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('position', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('position.delete', {
                parent: 'position',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/position/position-delete-dialog.html',
                        controller: 'PositionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Position', function(Position) {
                                return Position.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('position', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
