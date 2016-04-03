'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bill', {
                parent: 'app',
                url: '/bills',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Bills'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/bill/bills.html',
                        controller: 'BillController'
                    }
                },
                resolve: {
                	deps: ['$ocLazyLoad',
                           function( $ocLazyLoad ){
                             return $ocLazyLoad.load('ui.select').then(
                                 function(){
                                     return $ocLazyLoad.load(['js/controllers/select.js']);
                                 }
                             );
                    }]
                }
            })
            .state('bill.detail', {
                parent: 'app',
                url: '/bill/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Bill'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/bill/bill-detail.html',
                        controller: 'BillDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Bill', function($stateParams, Bill) {
                        return Bill.get({id : $stateParams.id});
                    }]
                }
            })
            .state('bill.new', {
                parent: 'reservation.detail',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bill/bill-dialog.html',
                        controller: 'BillDialogController',
                        size: 'lg',
                        resolve: {
                        	entity: ['Bill', function(Bill) {
                                return Bill.createByReservationId({reservationId : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('reservation.detail', {id:$stateParams.id}, { reload: true });
                    }, function() {
                        $state.go('reservation.detail',{id:$stateParams.id});
                    })
                }]
            })
            .state('bill.edit', {
                parent: 'reservation.detail',
                url: '/{idBill}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bill/bill-dialog-update.html',
                        controller: 'BillDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Bill', function(Bill) {
                                return Bill.get({id : $stateParams.idBill});
                            }]
                        }
                    }).result.then(function(result) {
                    	$state.go('reservation.detail', {id:$stateParams.id}, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('bill.delete', {
                parent: 'bill',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bill/bill-delete-dialog.html',
                        controller: 'BillDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Bill', function(Bill) {
                                return Bill.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bill', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('bill.printer', {
                parent: 'bill',
                url: '/printer/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bill/bill-printer.html',
                        controller: 'BillPrinterController',
                        size: 'lg',
                        resolve: {
//                            entity: ['Bill_service', function(Bill_service) {
//                                return Bill_service.get({id : $stateParams.id});
//                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bill', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            ;
    });
