'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('funiture', {
                parent: 'entity',
                url: '/funitures',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Funitures'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/funiture/funitures.html',
                        controller: 'FunitureController'
                    }
                },
                resolve: {
                }
            })
            .state('funiture.detail', {
                parent: 'entity',
                url: '/funiture/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Funiture'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/funiture/funiture-detail.html',
                        controller: 'FunitureDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Funiture', function($stateParams, Funiture) {
                        return Funiture.get({id : $stateParams.id});
                    }]
                }
            })
            .state('funiture.new', {
                parent: 'funiture',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/funiture/funiture-dialog.html',
                        controller: 'FunitureDialogController',
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
                        $state.go('funiture', null, { reload: true });
                    }, function() {
                        $state.go('funiture');
                    })
                }]
            })
            .state('funiture.edit', {
                parent: 'funiture',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/funiture/funiture-dialog.html',
                        controller: 'FunitureDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Funiture', function(Funiture) {
                                return Funiture.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('funiture', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('funiture.delete', {
                parent: 'funiture',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/funiture/funiture-delete-dialog.html',
                        controller: 'FunitureDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Funiture', function(Funiture) {
                                return Funiture.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('funiture', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
