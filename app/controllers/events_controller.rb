class EventsController < ApplicationController
	def new
	end

	def create
		render plain: params[:event].inspect
	end
end
