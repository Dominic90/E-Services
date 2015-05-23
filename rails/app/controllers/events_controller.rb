class EventsController < ApplicationController

	require 'socket'

	def index
		@events = Event.all

		#s = TCPSocket.new 'localhost', 11111
		#s.puts "hello"
		#s.puts "world"
		#puts s.gets
		#s.close
	end

	def show
    	@event = Event.find(params[:id])
	end

	def new
		@event = Event.new
	end

	def edit
		@event = Event.find(params[:id])
	end

	def create
		@event = Event.new(event_params)
		@event.user_id = current_user.id
    connection = Connection.where(used: false).first
 		@event.connection_id = connection.id
    connection.used = true
    connection.save

	 	if @event.save
    		redirect_to @event
		else
    		render 'new'
		end
	end

	def update
		@event = Event.find(params[:id])
 
		if @event.update(event_params)
			redirect_to @event
		else
			render 'edit'
		end
	end

	def destroy
		@event = Event.find(params[:id])
		@event.destroy
 
		redirect_to events_path
	end

	private
	 	def event_params
	    	params.require(:event).permit(:title, :description)
		end
end
