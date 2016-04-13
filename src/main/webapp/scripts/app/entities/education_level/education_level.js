'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('education_level', {
                parent: 'app',
                url: '/education_levels',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Education_levels'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/education_level/education_levels.html',
                        controller: 'Education_levelController'
                    }
                },
                resolve: {
                }
            })
            .state('education_level.detail', {
                parent: 'app',
                url: '/education_level/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Education_level'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/education_level/education_level-detail.html',
                        controller: 'Education_levelDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Education_level', function($stateParams, Education_level) {
                        return Education_level.get({id : $stateParams.id});
                    }]
                }
            })
            .state('education_level.new', {
                parent: 'education_level',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/education_level/education_level-dialog.html',
                        controller: 'Education_levelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    level: null,
                                    decription: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('education_level', null, { reload: true });
                    }, function() {
                        $state.go('education_level');
                    })
                }]
            })
            .state('education_level.edit', {
                parent: 'education_level',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/education_level/education_level-dialog.html',
                        controller: 'Education_levelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Education_level', function(Education_level) {
                                return Education_level.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('education_level', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('education_level.delete', {
                parent: 'education_level',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/education_level/education_level-delete-dialog.html',
                        controller: 'Education_levelDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Education_level', function(Education_level) {
                                return Education_level.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('education_level', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
