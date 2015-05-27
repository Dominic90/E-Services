class EventsController < ApplicationController

	require 'socket'

	def index
		@events = Event.all
	end

	def show
  	@event = Event.find(params[:id])
  	@connection = @event.connection
  	gon.kurento = @connection.ip + ":" + @connection.port.to_s + "/" + @event.url
  	#gon.push({
		#	kurento_ip => @connection.ip, 
		#	kurento_port => @connection.port,
		#	kurento_url => @event.url
		#})

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
    @connection = Connection.where(used: false).first
 		@event.connection_id = @connection.id
    @connection.used = true

    s = TCPSocket.new 'localhost', 9999
    s.puts "start node"
		s.puts @connection.ip
		s.puts @connection.port
		s.puts "call"
		puts s.gets
		s.close

    @connection.save

    @event.url = "call"
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
		connection = @event.connection
		@event.destroy
		connection.used = false
 		
		s = TCPSocket.new 'localhost', 9999
		s.puts "stop node"
		s.puts connection.ip
		s.puts connection.port
		puts s.gets
		s.close

 		connection.save
		redirect_to events_path
	end

	private
	 	def event_params
	    	params.require(:event).permit(:title, :description)
		end
end
